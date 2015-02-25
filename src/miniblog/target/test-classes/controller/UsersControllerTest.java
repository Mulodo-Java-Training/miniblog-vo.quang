package controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import javax.ws.rs.core.Response;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mulodo.miniblog.controller.UsersController;
import com.mulodo.miniblog.model.Tokens;
import com.mulodo.miniblog.model.Users;
import com.mulodo.miniblog.resteasy.form.LoginForm;
import com.mulodo.miniblog.service.TokensService;
import com.mulodo.miniblog.service.UsersService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/test/resources/TestControllerContext.xml")
public class UsersControllerTest 
{
	@Autowired
	UsersController usersController;
	@Autowired
	UsersService usersService;
	@Autowired
	TokensService tokensService;
	
	/**
	 * Test register success
	 * 200
	 * 
	 * */
	@Test
	public void registerTestSuccess() 
	{	
		Users user = new Users();
		user.setUsername("testsuccess");
		user.setPassword("123456");
		user.setFirstname("test");
		user.setLastname("test");
		user.setEmail("testsuccess@gmail.com");
		user.setImage("image.jpg");
		
		Response resp = usersController.register(user);
		assertEquals(200, resp.getStatus());			
	}
	
	/**
	 * Test register 
	 * input failed
	 * 1001
	 * 
	 * */
	@Test
	public void registerTestFail1() 
	{
		Users user = new Users();
		user.setUsername("");
		user.setPassword("");
		user.setFirstname("");
		user.setLastname("");
		user.setEmail("");		
		
		Response resp = usersController.register(user);
		assertEquals(1001, resp.getStatus());				
	}
	
	/**
	 * Test register
	 * username existed
	 * 2009
	 * 
	 * */
	@Test
	public void registerTestFail2() 
	{
		Users user = new Users();
		user.setUsername("testusername");
		user.setPassword("123456");
		user.setFirstname("test");
		user.setLastname("test");
		user.setEmail("test1@gmail.com");		
		user.setCreated_at(new Date());
		user.setModified_at(new Date());
		
		Response resp = usersController.register(user);
		assertEquals(2009, resp.getStatus());				
	}
	
	/**
	 * Test register
	 * email existed
	 * 2010
	 * 
	 * */
	@Test
	public void registerTestFail3() 
	{
		Users user = new Users();
		user.setUsername("test");
		user.setPassword("123456");
		user.setFirstname("test");
		user.setLastname("test");
		user.setEmail("testEmailExist@gmail.com");		
		
		Response resp = usersController.register(user);
		assertEquals(2010, resp.getStatus());				
	}
	
	/**
	 * Test register
	 * 9001
	 * 
	 * */
	@Test
	public void registerTestFail4() 
	{
		Users user = new Users();
		user.setUsername("test");
		user.setPassword("123456");
		user.setFirstname("test");
		user.setLastname("test");
		user.setEmail("test@gmail.com");
		user.setCreated_at(new Date());
		
		Response resp = usersController.register(user);
		assertEquals(9001, resp.getStatus());				
	}
	
	/**
	 * Test login success
	 * 200
	 * 
	 * */
	@Test
	public void loginTestSuccess()
	{
		LoginForm data = new LoginForm();
		data.setUsername("abc");
		data.setPassword("123456");
		
		Response resp = usersController.login(data);
		Tokens token = new Tokens();
		token.setAccess_token("abc123xyz");
		// Check new token has created when user login success.
		assertTrue(tokensService.isCreateToken(token));		
		assertEquals(200, resp.getStatus());
	}
	
	/**
	 * Test login failed
	 * 9001
	 * username empty
	 * 
	 * */
	@Test
	public void loginTestFailed1()
	{
		LoginForm data = new LoginForm();
		data.setUsername("");
		data.setPassword("123456");
		
		Response resp = usersController.login(data);
		assertEquals(1001, resp.getStatus());
	}
	
	/**
	 * Test login failed
	 * 9001
	 * password empty
	 * 
	 * */
	@Test
	public void loginTestFailed2()
	{
		LoginForm data = new LoginForm();
		data.setUsername("abc");
		data.setPassword("");
		
		Response resp = usersController.login(data);
		assertEquals(1001, resp.getStatus());
	}
	
	/**
	 * Test logout success
	 * 200
	 * 
	 * */
	@Test
	public void logoutSuccess()
	{	
		String access_token = "abc123xyz";
		
		Response resp = usersController.logout(access_token);
		assertEquals(200, resp.getStatus());
	}
	
	/**
	 * Test logout failed
	 * 9002
	 * access_token empty
	 * 
	 * */
	@Test
	public void logoutFailed1()
	{	
		String access_token = "";
		
		Response resp = usersController.logout(access_token);
		assertEquals(9002, resp.getStatus());
	}
	
	/**
	 * Test logout failed
	 * 9001
	 * Error
	 * 
	 * */
	@Test
	public void logoutFailed2()
	{	
		String access_token = "tokenSearchFailed";
		
		Response resp = usersController.logout(access_token);
		assertEquals(9001, resp.getStatus());
	}
	
	/**
	 * Test UpdateUserInfo success
	 * 200
	 * 
	 * */
	@Test
	public void updateUserInfoTestSuccess() 
	{
		String access_token = "abc123xyz";
		Users data = new Users();
		data.setUsername("testUsername");
		data.setFirstname("testFirstname");
		data.setLastname("testLastname");
		
		Response resp = usersController.updateUserInfo(1, access_token, data);
		assertEquals(200, resp.getStatus());		
	}
	
	/**
	 * Test UpdateUserInfo failed 1
	 * 1002
	 * Token expired
	 * 
	 * */
	@Test
	public void updateUserInfoTestFailed1() 
	{
		String access_token = "xxxxxx";
		Users data = new Users();
		data.setUsername("testUsername");
		data.setFirstname("testFirstname");
		data.setLastname("testLastname");
		
		Response resp = usersController.updateUserInfo(1, access_token, data);
		assertEquals(1002, resp.getStatus());		
	}
	
	/**
	 * Test UpdateUserInfo failed 2
	 * 1001
	 * Input failed
	 * username empty
	 * 
	 * */
	@Test
	public void updateUserInfoTestFailed2() 
	{
		String access_token = "abc123xyz";
		Users data = new Users();
		data.setUsername("");
		data.setFirstname("testFirstname");
		data.setLastname("testLastname");
		
		Response resp = usersController.updateUserInfo(1, access_token, data);
		assertEquals(1001, resp.getStatus());		
	}
	
	/**
	 * Test UpdateUserInfo failed 3
	 * 1001
	 * Input failed
	 * firstname empty
	 * 
	 * */
	@Test
	public void updateUserInfoTestFailed3() 
	{
		String access_token = "abc123xyz";
		Users data = new Users();
		data.setUsername("testUsername");
		data.setFirstname("");
		data.setLastname("testLastname");
		
		Response resp = usersController.updateUserInfo(1, access_token, data);
		assertEquals(1001, resp.getStatus());		
	}
	
	/**
	 * Test UpdateUserInfo failed 4
	 * 1001
	 * Input failed
	 * lastname empty
	 * 
	 * */
	@Test
	public void updateUserInfoTestFailed4() 
	{
		String access_token = "abc123xyz";
		Users data = new Users();
		data.setUsername("testUsername");
		data.setFirstname("testFirstname");
		data.setLastname("");
		
		Response resp = usersController.updateUserInfo(1, access_token, data);
		assertEquals(1001, resp.getStatus());		
	}
	
	/**
	 * Test UpdateUserInfo failed 5
	 * 1001
	 * Input failed
	 * username, firstname, lastname empty
	 * 
	 * */
	@Test
	public void updateUserInfoTestFailed5() 
	{
		String access_token = "abc123xyz";
		Users data = new Users();
		data.setUsername("");
		data.setFirstname("");
		data.setLastname("");
		
		Response resp = usersController.updateUserInfo(1, access_token, data);
		assertEquals(1001, resp.getStatus());		
	}
	
	/**
	 * Test UpdateUserInfo failed 6
	 * 9001
	 * Error
	 * 
	 * */
	@Test
	public void updateUserInfoTestFailed6() 
	{
		String access_token = "abc123xyz";
		Users data = new Users();
		data.setUsername("testUsernameFailed");
		data.setFirstname("testFirstname");
		data.setLastname("testLastname");
		
		Response resp = usersController.updateUserInfo(1, access_token, data);
		assertEquals(9001, resp.getStatus());		
	}
	
	/**
	 * Test getUserInfo success
	 * 200
	 * 
	 * */
	@Test
	public void getUserInfoTestSuccess() 
	{		
		Response resp = usersController.getUserInfo(1);
		assertEquals(200, resp.getStatus());		
	}
	
	/**
	 * Test getUserInfo failed
	 * 9001
	 * Error
	 * 
	 * */
	@Test
	public void getUserInfoTestFailed() 
	{		
		Response resp = usersController.getUserInfo(0);
//		ResponseFormat rf = (ResponseFormat) resp.getEntity();
//		Users user = (Users) rf.data;
		assertEquals(9001, resp.getStatus());		
	}
	
	/**
	 * Test changePassword success
	 * 200
	 * 
	 * */
	@Test
	public void changePasswordTestSuccess() 
	{		
		int id = 1;
		String currentPassword = "123456";
		String newPassword = "abc123";
		
		Response resp = usersController.changePassword(id, currentPassword, newPassword);		
		assertEquals(200, resp.getStatus());		
	}
	
//	/**
//	 * Test changePassword failed 1
//	 * 1001
//	 * Input failed
//	 * 
//	 * */
//	@Test
//	public void changePasswordTestFailed1() 
//	{		
//		int id = 1;
//		String currentPassword = "123456";
//		String newPassword = "abc123";
//		
//		Response resp = usersController.changePassword(id, currentPassword, newPassword);		
//		assertEquals(200, resp.getStatus());		
//	}
//	
//	/**
//	 * Test changePassword failed 2
//	 * 2012
//	 * User is not existed
//	 * 
//	 * */
//	@Test
//	public void changePasswordTestFailed2() 
//	{		
//		int id = 1;
//		String currentPassword = "123456";
//		String newPassword = "abc123";
//		
//		Response resp = usersController.changePassword(id, currentPassword, newPassword);		
//		assertEquals(200, resp.getStatus());		
//	}
//	
//	/**
//	 * Test changePassword failed 3
//	 * 2007
//	 * Password invalid
//	 * 
//	 * */
//	@Test
//	public void changePasswordTestFailed3() 
//	{		
//		int id = 1;
//		String currentPassword = "123456";
//		String newPassword = "abc123";
//		
//		Response resp = usersController.changePassword(id, currentPassword, newPassword);		
//		assertEquals(200, resp.getStatus());		
//	}
}
