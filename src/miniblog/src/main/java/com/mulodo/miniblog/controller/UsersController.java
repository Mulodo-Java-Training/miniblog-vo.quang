package com.mulodo.miniblog.controller;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.Form;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.mulodo.miniblog.encryption.Encryption;
import com.mulodo.miniblog.model.Tokens;
import com.mulodo.miniblog.model.Users;
import com.mulodo.miniblog.responseformat.Meta;
import com.mulodo.miniblog.responseformat.Response;
import com.mulodo.miniblog.resteasy.form.LoginForm;
import com.mulodo.miniblog.service.TokensService;
import com.mulodo.miniblog.service.UsersService;

@Path("v1/users")
@Controller
public class UsersController 
{
	@Autowired
	UsersService usersService;
	@Autowired
	TokensService tokensService;

	@POST
	@Path("/register")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Valid
	public Response register(Users data) 
	{		
		Response res = new Response();
		Meta meta = new Meta();

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
												
						meta.setId(200);
						meta.setMessage("User created sucess");
						res.setMeta(meta);
						res.setData(data);
					} else {
						meta.setId(9001);
						meta.setMessage("Error");
						res.setMeta(meta);
					}
				} else {
					meta.setId(2010);
					meta.setMessage("Email is already existed");
					res.setMeta(meta);
				}
			} else {
				meta.setId(2009);
				meta.setMessage("Username is already existed");
				res.setMeta(meta);
			}
		} else {
			meta.setId(1001);
			meta.setMessage("Input failed.");
			res.setMeta(meta);
		}
		return res;
	}
	
	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response login(@Form LoginForm data) 
	{		
		Response res = new Response();
		Meta meta = new Meta();

		if (data.getUsername() == null || data.getUsername().isEmpty() ||
				data.getPassword() == null || data.getPassword().isEmpty()) {			
			meta.setId(1001);
			meta.setMessage("Input failed.");
			res.setMeta(meta);
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
			//token.setExpired(new Timestamp(System.currentTimeMillis() + 7*24*60*60*1000));
			token.setExpired(new Timestamp(System.currentTimeMillis() + 5000));
			token.setAccess_token(access_token);
			// Save token to db
			tokensService.isCreateToken(token);
			
			meta.setId(201);
			meta.setMessage("Login Success");
			res.setMeta(meta);
			res.setData(data);

		} else {
			meta.setId(9001);
			meta.setMessage("Error.");
			res.setMeta(meta);
		}
		return res;
	}

	@PUT
	@Path("/logout")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response logout(@FormParam("access_token") String access_token ) 
	{		
		Response res = new Response();
		Meta meta = new Meta();
		
		// Check access_token not null, no spacing
		if (access_token != null && !access_token.isEmpty() && access_token.matches(".*\\w.*")) {
		// Pass access_token to an token object
		Tokens token = new Tokens();
		token.setAccess_token(access_token);
		// Search token in db that match token with access_token provided
		Tokens tokenSearch = tokensService.searchToken(token);
		
		if (tokenSearch != null) {
			tokensService.isDeleteToken(tokenSearch);
			meta.setId(202);
			meta.setMessage("Logout success");
			res.setMeta(meta);
		}
		else {
			meta.setId(9001);
			meta.setMessage("Error.");
			res.setMeta(meta);
		}	
		}
		else {
			meta.setId(11111);
			meta.setMessage("Missing access token.");
			res.setMeta(meta);
		}			
		return res;
	}

	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateUserInfo(@PathParam("id") int id, Users data) 
	{ 		
		Response res = new Response();
		Meta meta = new Meta();

		// Check whether token valid or not
		if (tokensService.isCheckTokenValid(data.getAccess_token()) == false) {			
			meta.setId(1002);
			meta.setMessage("Access token has expired.");
			res.setMeta(meta);
		}
		else {
			// Validate user's input
			if (data.getUsername() != null && data.getUsername().matches(".*\\w.*") && 
				data.getLastname() != null && data.getLastname().matches(".*\\w.*") &&
				data.getFirstname() != null && data.getFirstname().matches(".*\\w.*")) {
				
				if (!usersService.isCheckUserExist(data.getUsername())) {
					
					Users user = new Users();
					user = usersService.getUserById(id);		
					user.setUsername(data.getUsername());
					user.setLastname(data.getLastname());
					user.setFirstname(data.getFirstname());
					user.setImage(data.getImage());
					user.setModified_at(Calendar.getInstance().getTime());
						
					if (usersService.isUpdateUserInfo(user) == true) {
						meta.setId(203);
						meta.setMessage("Update success.");
						res.setMeta(meta);
						res.setData(user);
					}
					else {
						meta.setId(9001);
						meta.setMessage("Error.");
						res.setMeta(meta);
					}			
				}
				else {
					meta.setId(2009);
					meta.setMessage("Username is already existed.");
					res.setMeta(meta);
				}
			}
			else {
				meta.setId(1001);
				meta.setMessage("Input failed.");
				res.setMeta(meta);
			}
		}
		return res;
	}
	
	@Path("/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserInfo(@PathParam("id") int id) 
	{		
		Response res = new Response();
		Meta meta = new Meta();
		
		// Get user by id
		Users user = usersService.getUserById(id);
		
		if (user != null) {
			meta.setId(204);
			meta.setMessage("Get user info success");
			res.setData(user);
			res.setMeta(meta);
		}
		else {
			meta.setId(9001);
			meta.setMessage("Error.");
			res.setMeta(meta);
		}		
		return res;
	}
		
	@PUT
	@Path("/changepassword")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response changePassword(@FormParam("id") int id, @FormParam("currentPassword") String currentPassword,
									@FormParam("newPassword") String newPassword) 
	{		
		Meta meta = new Meta();
		Response res = new Response();
		
		if (id == 0 || currentPassword == null || currentPassword.isEmpty() || 
				newPassword == null || newPassword.isEmpty()) {
			
			meta.setId(1001);
			meta.setMessage("Input failed.");
			res.setMeta(meta);
		}
		else {
			// Get user info by ID
			Users user = usersService.getUserById(id);
			
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
						//System.out.println("Delete success.");
					}								
				}			
				meta.setId(205);
				meta.setMessage("Change password success.");
				res.setMeta(meta);
				res.setData(user);
			}
			else {
				meta.setId(2007);
				meta.setMessage("Password id invalid.");
				res.setMeta(meta);
			}		
		}
		return res;
	}
	
	@GET
	@Path("/search/name={name}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response searchUserByName(@PathParam("name") String name) 
	{		
		Meta meta = new Meta();
		Response res = new Response();
		System.out.println("---------" + name);		
		if (name == null || name.isEmpty()) {
			meta.setId(1001);
			meta.setMessage("Input failed.");
			res.setMeta(meta);
		}
		
		//Get list user searched by firstname, lastname
		List<Users> listUser = usersService.getListUserByName(name);
		
		if (listUser != null  && listUser.size() != 0) {		
			meta.setId(205);
			meta.setMessage("Search success.");
			res.setMeta(meta);
			res.setData(listUser);		
		}
		else {
			meta.setId(9001);
			meta.setMessage("Error.");
			res.setMeta(meta);
		}		
		return res;
	}
}

