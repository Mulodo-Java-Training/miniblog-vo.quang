package com.mulodo.miniblog.controller;

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

import com.mulodo.miniblog.constants.Constants;
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
//					String encryptPass = user.getPassword();
//					user.setPassword(Encryption.hashSHA256(encryptPass));
					
					// Register a new user account
					if (usersService.register(user) == true) {						
//						// Create access_token when create account success.   
//						String access_token = Encryption.createToken(user.getId());
//						// Set access_token to user 
//						user.setAccess_token(access_token);
//						// Create token 
//						Tokens token = new Tokens();
//						// Current time
//						token.setCreated_at(new Timestamp(System.currentTimeMillis()));
//						// Expired in 7 days
//						token.setExpired(new Timestamp(System.currentTimeMillis() + 7*24*60*60*1000));
//						token.setAccess_token(access_token);
//						token.setUser_id(user.getId());						
						// Save to db
						tokensService.isCreateToken(user);
												
						rf.meta.id = Constants.CODE_200;
						rf.meta.message = Constants.USER_CREATE_SUCCESS;
						rf.data = user;
						return Response.status(Constants.CODE_200).entity(rf).build();
					} 
					else {
						rf.meta.id = Constants.CODE_9001;
						rf.meta.message = Constants.ERROR_MESSAGE;
						return Response.status(Constants.CODE_9001).entity(rf).build();
					}
				} 
				else {
					rf.meta.id = Constants.CODE_2010;
					rf.meta.message = Constants.USER_EMAIL_EXISTED;
					return Response.status(Constants.CODE_2010).entity(rf).build();
				}
			} 
			else {
				rf.meta.id = Constants.CODE_2009;
				rf.meta.message = Constants.USERNAME_EXISTED;
				return Response.status(Constants.CODE_2009).entity(rf).build();
			}
		} 
		else {
			rf.meta.id = Constants.CODE_1001;
			rf.meta.message = Constants.INPUT_FAILED;
			return Response.status(Constants.CODE_1001).entity(rf).build();
		}
	}
	
	@POST
	@Path("login")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response login(@Form LoginForm data) 
	{		
		ResponseFormat rf = new ResponseFormat();
		
		if (data.getUsername() == null || !data.getUsername().matches(Constants.REGEX_WHITE_SPACE) ||
			data.getPassword() == null || !data.getPassword().matches(Constants.REGEX_WHITE_SPACE)) {
			
			rf.meta.id = Constants.CODE_1001;
			rf.meta.message = Constants.INPUT_FAILED;
			return Response.status(Constants.CODE_1001).entity(rf).build();
		}

		/**
		 * Encrypt password in order to compare with encrypted password of user in database 
		 */
		String encryptPass = Encryption.hashSHA256(data.getPassword());
		
		if (usersService.isLogin(data.getUsername(), encryptPass) == true) {				
			// Get user by username 
			Users user = usersService.getUserByUsername(data.getUsername());
			// Create access_token for user
//			String access_token = Encryption.createToken(user.getId());
//			Tokens token = new Tokens();
//			token.setUser_id(user.getId());
//			// Current time
//			token.setCreated_at(new Timestamp(System.currentTimeMillis()));
//			// Expired in 7 days
//			token.setExpired(new Timestamp(System.currentTimeMillis() + 7*24*60*60*1000));
//			//token.setExpired(new Timestamp(System.currentTimeMillis() + 5000));
//			token.setAccess_token(access_token);
			// Save token to db
			tokensService.isCreateToken(user);
			
			rf.meta.id = Constants.CODE_200;
			rf.meta.message = Constants.USER_LOGIN_SUCCESS;
			rf.data = user;
			return Response.status(Constants.CODE_200).entity(rf).build();
		} 
		else {
			rf.meta.id = Constants.CODE_9001;
			rf.meta.message = Constants.ERROR_MESSAGE;
			return Response.status(Constants.CODE_9001).entity(rf).build();
		}
	}

	@POST
	@Path("logout")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response logout(@HeaderParam("access_token") String access_token ) 
	{		
		ResponseFormat rf = new ResponseFormat();
		
		// Check access_token not null, no spacing
		if (access_token != null && access_token.matches(Constants.REGEX_WHITE_SPACE)) {
			// Search token in db that matches token with access_token provided
			Tokens tokenSearch = tokensService.searchToken(access_token);
			if (tokenSearch != null) {
				Users user = usersService.getUserByToken(tokenSearch.getAccess_token());
//				tokensService.isDeleteToken(tokenSearch);
				tokensService.isDeleteTokenByUserId(user.getId());
				rf.meta.id = Constants.CODE_200;
				rf.meta.message = Constants.USER_LOGOUT_SUCCESS;
				return Response.status(Constants.CODE_200).entity(rf).build();
			}
			else {
				rf.meta.id = Constants.CODE_9001;
				rf.meta.message = Constants.ERROR_MESSAGE;
				return Response.status(Constants.CODE_9001).entity(rf).build();
			}	
		}
		else {
			rf.meta.id = Constants.CODE_9002;
			rf.meta.message = Constants.TOKEN_MISSING;
			return Response.status(Constants.CODE_9002).entity(rf).build();
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
			rf.meta.id = Constants.CODE_1002;
			rf.meta.message = Constants.TOKEN_EXPIRED;
			return Response.status(Constants.CODE_1002).entity(rf).build();
		}
		else {
			// Validate user's input
			if (data.getUsername() != null && data.getUsername().matches(Constants.REGEX_WHITE_SPACE) && 
				data.getLastname() != null && data.getLastname().matches(Constants.REGEX_WHITE_SPACE) &&
				data.getFirstname() != null && data.getFirstname().matches(Constants.REGEX_WHITE_SPACE)) {
									
				Users user = new Users();
				user = usersService.getUserById(id);		
				user.setUsername(data.getUsername());
				user.setLastname(data.getLastname());
				user.setFirstname(data.getFirstname());
				user.setImage(data.getImage());
				user.setModified_at(Calendar.getInstance().getTime());
					
				if (usersService.isUpdateUserInfo(user) == true) {
					rf.meta.id = Constants.CODE_200;
					rf.meta.message = Constants.USER_UPDATE_SUCCESS;
					rf.data = user;
					return Response.status(Constants.CODE_200).entity(rf).build();					
				}
				else {
					rf.meta.id = Constants.CODE_9001;
					rf.meta.message = Constants.ERROR_MESSAGE;
					return Response.status(Constants.CODE_9001).entity(rf).build();
				}			
			}
			else {
				rf.meta.id = Constants.CODE_1001;
				rf.meta.message = Constants.INPUT_FAILED;
				return Response.status(Constants.CODE_1001).entity(rf).build();
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
			rf.meta.id = Constants.CODE_200;
			rf.meta.message = Constants.USER_GET_USER_INFO_SUCCESS;
			rf.data = user;
			return Response.status(Constants.CODE_200).entity(rf).build();
		}
		else {
			rf.meta.id = Constants.CODE_9001;
			rf.meta.message = Constants.ERROR_MESSAGE;
			return Response.status(Constants.CODE_9001).entity(rf).build();
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
		if (id == 0 || currentPassword == null || !currentPassword.matches(Constants.REGEX_WHITE_SPACE) ||  
			newPassword == null || !newPassword.matches(Constants.REGEX_WHITE_SPACE)) {
			
			rf.meta.id = Constants.CODE_1001;
			rf.meta.message = Constants.INPUT_FAILED;
			return Response.status(Constants.CODE_1001).entity(rf).build();
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
					rf.meta.id = Constants.CODE_200;
					rf.meta.message = Constants.USER_CHANGE_PASS_SUCCESS;
					rf.data = user;
					return Response.status(Constants.CODE_200).entity(rf).build();
				}
				else {
					rf.meta.id = Constants.CODE_2007;
					rf.meta.message = Constants.USER_PASS_INVALID;
					return Response.status(Constants.CODE_2007).entity(rf).build();
				}		
			}
			else {
				rf.meta.id = Constants.CODE_2012;
				rf.meta.message = Constants.USER_NOT_EXIST;
				return Response.status(Constants.CODE_2012).entity(rf).build();
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
		if (name == null || !name.matches(Constants.REGEX_WHITE_SPACE)) {
			rf.meta.id = Constants.CODE_1001;
			rf.meta.message = Constants.INPUT_FAILED;
			return Response.status(Constants.CODE_1001).entity(rf).build();
		}
		
		//Get list user searched by firstname, lastname
		List<Users> listUser = usersService.getListUserByName(name);
		
//		if (listUser != null  && listUser.size() != 0) {
			rf.meta.id = Constants.CODE_200;
			rf.meta.message = Constants.USER_SEARCH_SUCCESS;
			rf.data = listUser;
			return Response.status(Constants.CODE_200).entity(rf).build();
//		}
//		else {
//			rf.meta.id = Constants.CODE_9001;
//			rf.meta.message = Constants.ERROR_MESSAGE;
//			return Response.status(Constants.CODE_9001).entity(rf).build();
//		}		
	}
}

