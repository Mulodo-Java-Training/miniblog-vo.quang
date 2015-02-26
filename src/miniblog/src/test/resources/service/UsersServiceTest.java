package service;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mulodo.miniblog.encryption.Encryption;
import com.mulodo.miniblog.model.Users;
import com.mulodo.miniblog.resteasy.form.LoginForm;
import com.mulodo.miniblog.service.TokensService;
import com.mulodo.miniblog.service.UsersService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/applicationContext.xml")
public class UsersServiceTest 
{
	@Autowired
	UsersService usersService;
	@Autowired
	TokensService tokensService;
	
	
	public void Register() 
	{	
//		usersService.isDeleteUser("testRegister");
		
		Users user = new Users();
		user.setUsername("testRegister");
		user.setPassword("123456");
		user.setLastname("testRegister");
		user.setFirstname("testRegister");
		user.setEmail("testRegister@gmail.com");
		user.setImage("image.jpg");
		user.setCreated_at(new Date());
		user.setModified_at(new Date());
		
		assertTrue(usersService.isValidateUser(user));
		assertTrue(!usersService.isCheckUserExist(user.getUsername()));
		assertTrue(!usersService.isCheckEmailExist(user.getEmail()));
		assertTrue(usersService.register(user));
		assertTrue(tokensService.isCreateToken(user));
		assertTrue(tokensService.isDeleteTokenByUserId(user.getId()));
		assertTrue(usersService.isDeleteUser(user.getUsername()));		
	}
	
	
	public void LoginUpdateChangePasswordLogout() 
	{
		// Register
//		assertTrue(usersService.isDeleteUser("testRegister"));
		
		Users user = new Users();
		user.setUsername("testRegister");
		user.setPassword("123456");
		user.setLastname("testRegister");
		user.setFirstname("testRegister");
		user.setEmail("testRegister@gmail.com");
		user.setImage("image.jpg");
		user.setCreated_at(new Date());
		user.setModified_at(new Date());
		
		assertTrue(usersService.isValidateUser(user));
		assertTrue(!usersService.isCheckUserExist(user.getUsername()));
		assertTrue(!usersService.isCheckEmailExist(user.getEmail()));
		assertTrue(usersService.register(user));
//		assertTrue(tokensService.isCreateToken(user));
		
		// Login
		LoginForm form = new LoginForm();
		form.setUsername("testRegister");
		form.setPassword(Encryption.hashSHA256("123456"));
		Users userLogin = new Users();
		userLogin = usersService.getUserByUsername(form.getUsername());
		String access_token = Encryption.createToken(user.getId());
		System.out.println(access_token);
		assertTrue(tokensService.isCreateToken(userLogin));		
		assertTrue(usersService.isLogin(form.getUsername(), form.getPassword()));
		
		// Update		
		assertTrue(tokensService.isCheckTokenValid(access_token));
		Users user1 = new Users();
		user1.setUsername("testUserUpdate");
		user1.setLastname("testUserUpdate");
		user1.setFirstname("testUserUpdate");
		assertTrue(usersService.isUpdateUserInfo(user1));
		// Get return user
		Users getUser = usersService.getUserById(user.getId());
		assertEquals(getUser.getUsername(), "testUserUpdate");
		assertEquals(getUser.getLastname(), "testUserUpdate");
		assertEquals(getUser.getFirstname(), "testUseUpdate");
		
		// Logout
		assertTrue(tokensService.isDeleteTokenByUserId(user.getId()));
		// Delete user
		assertTrue(usersService.isDeleteUser(user.getUsername()));		
	}
	
	@Test
	public void getUserInfoTest()
	{
		// Register
		assertTrue(usersService.isDeleteUser("testRegister"));
		
//		Users user = new Users();
//		user.setUsername("testRegister");
//		user.setPassword("123456");
//		user.setLastname("testRegister");
//		user.setFirstname("testRegister");
//		user.setEmail("testRegister@gmail.com");
//		user.setImage("image.jpg");
//		user.setCreated_at(new Date());
//		user.setModified_at(new Date());
//		
//		assertTrue(usersService.isValidateUser(user));
//		assertTrue(!usersService.isCheckUserExist(user.getUsername()));
//		assertTrue(!usersService.isCheckEmailExist(user.getEmail()));
//		assertTrue(usersService.register(user));
//		
//		// Login
//		LoginForm form = new LoginForm();
//		form.setUsername("testRegister");
//		form.setPassword(Encryption.hashSHA256("123456"));
//		Users userLogin = new Users();
//		userLogin = usersService.getUserByUsername(form.getUsername());
//		String access_token = Encryption.createToken(user.getId());
//		System.out.println(access_token);
//		assertTrue(tokensService.isCreateToken(userLogin));		
//		assertTrue(usersService.isLogin(form.getUsername(), form.getPassword()));
//		
//		Users user1 = new Users();
//		user1.setUsername("testGetUser");
//		user1.setLastname("testGetUser");
//		user1.setFirstname("testGetUser");
//		user1.setEmail("testGetUser@gmail.com");
//		// Create user1
//		assertTrue(usersService.register(user1));
//		
//		Users user2 = usersService.getUserById(user1.getId());
//		
//		assertEquals(user2.getUsername(), user1.getUsername());
//		assertEquals(user2.getLastname(), user1.getLastname());
//		assertEquals(user2.getFirstname(), user1.getFirstname());
//		assertEquals(user2.getEmail(), user1.getEmail());	
//		
//		// Logout
//		assertTrue(tokensService.isDeleteTokenByUserId(user.getId()));
//		// Delete user
//		assertTrue(usersService.isDeleteUser(user.getUsername()));
//		assertTrue(usersService.isDeleteUser(user1.getUsername()));
	}
	
}
