//package com.mulodo.miniblog.test.junit;
//
//import static org.junit.Assert.assertEquals;
//
//import javax.ws.rs.core.MediaType;
//
//import org.jboss.resteasy.client.ClientRequest;
//import org.jboss.resteasy.client.ClientResponse;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.mulodo.miniblog.controller.UsersController;
//import com.mulodo.miniblog.responseformat.Response;
//import com.mulodo.miniblog.service.UsersService;
//
//
//@SuppressWarnings("deprecation")
//public class UsersTest 
//{		
//	static final String ROOT_USER_URL = "http://localhost:8080/miniblog/v1/users";
//	
//	@Autowired
//	private UsersService usersService;
//	@Autowired
//	private UsersController usersController;
//	
//
//	
//	public void testRegister() throws Exception
//	{
//		
//		ClientRequest request = new ClientRequest(ROOT_USER_URL + "/register");
//		request.accept(MediaType.APPLICATION_JSON);
//		ClientResponse<Response> response = null;
//		
//		String userInput = "{\"username\": \"register\"," + "\"password\": \"123456\","
//                + "\"lastname\": \"one\",\"firstname\": \"test\"," + "\"email\": \"testaddd@yahoo.com\","
//                + "\"image\": \"image.jpg\"," + "\"created_at\": \"2015-01-01\","
//                + "\"modified_at\": \"2015-01-01\"}";
//		
//		request.body(MediaType.APPLICATION_JSON, userInput);
//		response = request.post(Response.class);
//		assertEquals(200, response.getStatus());
//		
//		System.out.println(response.getEntity(String.class) + "----");
//				
//	}
//	
//	@Test
//	public void testUserExisted() throws Exception 
//	{
//		ClientRequest request = new ClientRequest(ROOT_USER_URL + "/register");
//		
//		ClientResponse<Response> response = null;
//
//		request.accept(MediaType.APPLICATION_JSON);
//		
//		String userInput = "{\"username\": \"testadd\"," + "\"password\": \"123456\","
//		                + "\"lastname\": \"one\",\"firstname\": \"test\"," + "\"email\": \"testadd@yahoo.com\","
//		                + "\"image\": \"image.jpg\"," + "\"created_at\": \"2015-01-01\","
//		                + "\"modified_at\": \"2015-01-01\"}";
//			
//		request.body(MediaType.APPLICATION_JSON, userInput);
//		response = request.post(Response.class);
//		assertEquals(2009, response.getStatus());
//				
//	}
//	
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
//}
