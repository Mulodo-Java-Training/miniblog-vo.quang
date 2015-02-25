package com.mulodo.miniblog.controller;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.Form;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.mulodo.miniblog.encryption.Encryption;
import com.mulodo.miniblog.model.Tokens;
import com.mulodo.miniblog.model.Users;
import com.mulodo.miniblog.responseformat.ResponseFormat;
import com.mulodo.miniblog.resteasy.form.LoginForm;
import com.mulodo.miniblog.service.TokensService;
import com.mulodo.miniblog.service.UsersService;

@Path("v1/users")
@Controller
@Produces(MediaType.APPLICATION_JSON)
public class UsersController 
{
	@Autowired
	UsersService usersService;
	@Autowired
	TokensService tokensService;

	
	@POST
	@Path("register")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Valid
	public Response register(Users data) 
	{		
		ResponseFormat rf = new ResponseFormat();

		if (usersService.isValidateUser(data)) {
			if (!usersService.isCheckUserExist(data.getUsername())) {
				if (!usersService.isCheckEmailExist(data.getEmail())) {
					Users user = new Users();
					user.setUsername(data.getUsername());
					user.setPassword(data.getPassword());
					user.setLastname(data.getLastname());
					user.setFirstname(data.getFirstname());
					user.setEmail(data.getEmail());
					user.setImage(data.getImage());
					user.setCreated_at(Calendar.getInstance().getTime());
					user.setModified_at(Calendar.getInstance().getTime());
					
					// Set encrypted password to user
					String encryptPass = user.getPassword();
					user.setPassword(Encryption.hashSHA256(encryptPass));
					
					// Register a new user account
					if (usersService.register(user) == true) {						
						// Create access_token when create account success.   
						String access_token = Encryption.createToken(user.getId());
						// Set access_token to user 
						user.setAccess_token(access_token);
						// Create token 
						Tokens token = new Tokens();
						// Current time
						token.setCreated_at(new Timestamp(System.currentTimeMillis()));
						// Expired in 7 days
						token.setExpired(new Timestamp(System.currentTimeMillis() + 7*24*60*60*1000));
						token.setAccess_token(access_token);
						token.setUser_id(user.getId());						
						// Save to db
						tokensService.isCreateToken(token);
												
						rf.meta.id = 200;
						rf.meta.message = "User created sucess";
						rf.data = user;
						return Response.status(200).entity(rf).build();
					} else {
						rf.meta.id = 9001;
						rf.meta.message = "Error.";
						return Response.status(9001).entity(rf).build();
					}
				} else {
					rf.meta.id = 2010;
					rf.meta.message = "Email is already existed";
					return Response.status(2010).entity(rf).build();
				}
			} else {
				rf.meta.id = 2009;
				rf.meta.message = "Username is already existed";
				return Response.status(2009).entity(rf).build();
			}
		} else {
			rf.meta.id = 1001;
			rf.meta.message = "Input failed.";
			return Response.status(1001).entity(rf).build();
		}
	}
	
	@POST
	@Path("login")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response login(@Form LoginForm data) 
	{		
		ResponseFormat rf = new ResponseFormat();
		
		if (data.getUsername() == null || data.getUsername().isEmpty() ||
			data.getPassword() == null || data.getPassword().isEmpty()) {
			
			rf.meta.id = 1001;
			rf.meta.message = "Input failed.";
			return Response.status(1001).entity(rf).build();
		}

		/**
		 * Encrypt password in order to compare with encrypted password of user in database 
		 */
		String encryptPass = Encryption.hashSHA256(data.getPassword());
		
		if (usersService.isLogin(data.getUsername(), encryptPass) == true) {				
			// Get user by username 
			Users user = usersService.getUserByUsername(data.getUsername());
			// Create access_token for user
			String access_token = Encryption.createToken(user.getId());
			Tokens token = new Tokens();
			token.setUser_id(user.getId());
			// Current time
			token.setCreated_at(new Timestamp(System.currentTimeMillis()));
			// Expired in 7 days
			token.setExpired(new Timestamp(System.currentTimeMillis() + 7*24*60*60*1000));
			//token.setExpired(new Timestamp(System.currentTimeMillis() + 5000));
			token.setAccess_token(access_token);
			// Save token to db
			tokensService.isCreateToken(token);
			
			rf.meta.id = 200;
			rf.meta.message = "Login Success";
			rf.data = user;
			return Response.status(200).entity(rf).build();
		} 
		else {
			rf.meta.id = 9001;
			rf.meta.message = "Error.";
			return Response.status(9001).entity(rf).build();
		}
	}

	@PUT
	@Path("logout")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response logout(@FormParam("access_token") String access_token ) 
	{		
		ResponseFormat rf = new ResponseFormat();
		
		// Check access_token not null, no spacing
		if (access_token != null && access_token.matches(".*\\w.*")) {
			// Search token in db that matches token with access_token provided
			Tokens tokenSearch = tokensService.searchToken(access_token);

			if (tokenSearch != null) {
				tokensService.isDeleteToken(tokenSearch);
				rf.meta.id = 200;
				rf.meta.message = "Logout success";
				return Response.status(200).entity(rf).build();
			}
			else {
				rf.meta.id = 9001;
				rf.meta.message = "Error.";
				return Response.status(9001).entity(rf).build();
			}	
		}
		else {
			rf.meta.id = 9002;
			rf.meta.message = "Missing token. Please login";
			return Response.status(9002).entity(rf).build();
		}			
	}

	@PUT
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateUserInfo(@PathParam("id") int id, @HeaderParam("access_token") String access_token, Users data) 
	{ 		
		ResponseFormat rf = new ResponseFormat();

		// Check whether token expired or not
		if (tokensService.isCheckTokenValid(access_token) == false) {	
			rf.meta.id = 1002;
			rf.meta.message = "Access token has expired.";
			return Response.status(1002).entity(rf).build();
		}
		else {
			// Validate user's input
			if (data.getUsername() != null && data.getUsername().matches(".*\\w.*") && 
				data.getLastname() != null && data.getLastname().matches(".*\\w.*") &&
				data.getFirstname() != null && data.getFirstname().matches(".*\\w.*")) {
									
				Users user = new Users();
				user = usersService.getUserById(id);		
				user.setUsername(data.getUsername());
				user.setLastname(data.getLastname());
				user.setFirstname(data.getFirstname());
				user.setImage(data.getImage());
				user.setModified_at(Calendar.getInstance().getTime());
					
				if (usersService.isUpdateUserInfo(user) == true) {
					rf.meta.id = 200;
					rf.meta.message = "Update success.";
					rf.data = user;
					return Response.status(200).entity(rf).build();					
				}
				else {
					rf.meta.id = 9001;
					rf.meta.message = "Error.";
					return Response.status(9001).entity(rf).build();
				}			
			}
			else {
				rf.meta.id = 1001;
				rf.meta.message = "Input failed.";
				return Response.status(1001).entity(rf).build();
			}
		}
	}
	
	@Path("{id}")
	@GET	
	public Response getUserInfo(@PathParam("id") int id) 
	{		
		ResponseFormat rf = new ResponseFormat();
		
		// Get user by id
		Users user = usersService.getUserById(id);		
		if (user != null) {
			rf.meta.id = 200;
			rf.meta.message = "Get user info success";
			rf.data = user;
			return Response.status(200).entity(rf).build();
		}
		else {
			rf.meta.id = 9001;
			rf.meta.message = "Error.";
			return Response.status(9001).entity(rf).build();
		}		
	}
		
	@PUT
	@Path("changepassword")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response changePassword(@FormParam("id") int id, @FormParam("currentPassword") String currentPassword,
									@FormParam("newPassword") String newPassword) 
	{		
		ResponseFormat rf = new ResponseFormat();
		if (id == 0 || currentPassword == null || !currentPassword.matches(".*\\w.*") ||  
			newPassword == null || !newPassword.matches(".*\\w.*")) {
			
			rf.meta.id = 1001;
			rf.meta.message = "Input failed.";
			return Response.status(1001).entity(rf).build();
		}
		else {
			// Get user info by ID
			Users user = usersService.getUserById(id);
			// Check user existed
			if (user != null) {
				// Encrypt password in order to compare with password in db
				String passwordHash = Encryption.hashSHA256(currentPassword);
				
				// if two password are equal, allow changing to new password
				if (user.getPassword().equals(passwordHash)) {		
					//Encrypt new password and set to user's password
					user.setPassword(Encryption.hashSHA256(newPassword));
					user.setModified_at(Calendar.getInstance().getTime());				
					// Update new password
					usersService.isUpdateUserInfo(user);					
					//Delete old tokens of previous password
					List<Tokens> listToken = tokensService.getTokenByUserId(user.getId());
					
					if (listToken != null) {				
						for (Tokens item : listToken) {
							tokensService.isDeleteToken(item);
						}								
					}	
					rf.meta.id = 200;
					rf.meta.message = "Change password success.";
					rf.data = user;
					return Response.status(200).entity(rf).build();
				}
				else {
					rf.meta.id = 2007;
					rf.meta.message = "Password id invalid.";
					return Response.status(2007).entity(rf).build();
				}		
			}
			else {
				rf.meta.id = 2012;
				rf.meta.message = "User is not existed";
				return Response.status(2012).entity(rf).build();
			}
		}
	}
	
	@GET
	@Path("search/name={name}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response searchUserByName(@PathParam("name") String name) 
	{		
		ResponseFormat rf = new ResponseFormat();
		//name.matches(".*\\w.*")		
		if (name == null || name.isEmpty()) {
			rf.meta.id = 1001;
			rf.meta.message = "Input failed.";
			return Response.status(1001).entity(rf).build();
		}
		
		//Get list user searched by firstname, lastname
		List<Users> listUser = usersService.getListUserByName(name);
		
		if (listUser != null  && listUser.size() != 0) {
			rf.meta.id = 200;
			rf.meta.message = "Search success.";
			rf.data = listUser;
			return Response.status(200).entity(rf).build();
		}
		else {
			rf.meta.id = 9001;
			rf.meta.message = "Error.";
			return Response.status(9001).entity(rf).build();
		}		
	}
}

