package com.mulodo.miniblog.test.junit;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.mulodo.miniblog.controller.UsersController;
import com.mulodo.miniblog.model.Users;
import com.mulodo.miniblog.responseformat.ResponseFormat;
import com.mulodo.miniblog.service.UsersService;


@SuppressWarnings("deprecation")
public class UsersTest 
{		
	static final String ROOT_USER_URL = "http://localhost:8080/miniblog/v1/users";
	
	@Autowired
	private UsersService usersService;
	@Autowired
	private UsersController usersController;
	

	@Test
	public void testRegister() throws Exception
	{
		
		ClientRequest request = new ClientRequest(ROOT_USER_URL + "/register");
		request.accept(MediaType.APPLICATION_JSON);
		ClientResponse<String> response = null;
		
		String userInput = "{\"username\": \"abcd\"," + "\"password\": \"123456\","
                + "\"lastname\": \"one\",\"firstname\": \"test\"," + "\"email\": \"test_add@yahoo.com\","
                + "\"image\": \"image.jpg\"," + "\"created_at\": \"2015-01-01\","
                + "\"modified_at\": \"2015-01-01\"}";
		
		request.body(MediaType.APPLICATION_JSON, userInput);
		response = request.post(String.class);
		assertEquals(200, response.getStatus());
		
				
	}
	
	@Test
	public void testUserExisted() throws Exception 
	{
		ClientRequest request = new ClientRequest(ROOT_USER_URL + "/register");
		
		ClientResponse<Response> response = null;

		request.accept(MediaType.APPLICATION_JSON);
		
		String userInput = "{\"username\": \"testadd\"," + "\"password\": \"123456\","
		                + "\"lastname\": \"one\",\"firstname\": \"test\"," + "\"email\": \"testadd@yahoo.com\","
		                + "\"image\": \"image.jpg\"," + "\"created_at\": \"2015-01-01\","
		                + "\"modified_at\": \"2015-01-01\"}";
			
		request.body(MediaType.APPLICATION_JSON, userInput);
		response = request.post(Response.class);
		assertEquals(2009, response.getStatus());
				
	}
	
//	@Test
//	public void testEmailExisted() throws Exception 
//	{
//		ClientRequest request = new ClientRequest(ROOT_USER_URL + "/register");
//		
//		ClientResponse<Response> response = null;
//
//		request.accept(MediaType.APPLICATION_JSON);
//		
//		String userInput = "{\"username\": \"testadd1\"," + "\"password\": \"123456\","
//		                + "\"lastname\": \"one\",\"firstname\": \"test\"," + "\"email\": \"testadd@yahoo.com\","
//		                + "\"image\": \"image.jpg\"," + "\"created_at\": \"2015-01-01\","
//		                + "\"modified_at\": \"2015-01-01\"}";
//			
//		request.body(MediaType.APPLICATION_JSON, userInput);
//		response = request.post(Response.class);
//		assertEquals(2010, response.getStatus());
//				
//	}
}
