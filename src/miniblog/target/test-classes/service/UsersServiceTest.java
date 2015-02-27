package service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

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
	
	
//	public void registerGetUserInfoTest() 
//	{	
//		// Register
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
//		assertTrue(tokensService.isCreateToken(user));
//		
//		// Get User Info
//		Users user1 = new Users();
//		user1.setUsername("testGetUser");
//		user1.setPassword("123456");
//		user1.setLastname("testGetUser");
//		user1.setFirstname("testGetUser");
//		user1.setEmail("testGetUser@gmail.com");
//		// Create user1
//		assertTrue(usersService.register(user1));
//		
//		// Get User by Id
//		Users getUser1 = usersService.getUserById(user1.getId());		
//		// Test user1	
//		assertEquals("testGetUser", getUser1.getUsername());
//		assertEquals("testGetUser", getUser1.getLastname());
//		assertEquals("testGetUser", getUser1.getFirstname());
//		assertEquals("testGetUser@gmail.com", getUser1.getEmail());	
//		// Delete user1
//		assertTrue(usersService.isDeleteUser(user1.getUsername()));
//		
//		// Delete token 
//		assertTrue(tokensService.isDeleteTokenByUserId(user.getId()));
//		// Delete user
//		assertTrue(usersService.isDeleteUser(user.getUsername()));		
//	}
	
	@Test
	public void userTest()
	{
		// Register		
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
		// Delete token
		tokensService.isDeleteTokenByUserId(user.getId());
		
		
		// Login
		LoginForm form = new LoginForm();
		form.setUsername("testRegister");
		form.setPassword(Encryption.hashSHA256("123456"));
		Users userLogin = new Users();
		userLogin = usersService.getUserByUsername(form.getUsername());
		assertTrue(tokensService.isCreateToken(userLogin));			
		assertTrue(usersService.isLogin(userLogin.getUsername(), userLogin.getPassword()));
		
		
		// Change Password
		userLogin.setPassword(Encryption.hashSHA256("abc123"));
		userLogin.setModified_at(new Date());
		assertTrue(usersService.isUpdateUserInfo(userLogin));		
		// Check changed password matches actual password
		String passChange = Encryption.hashSHA256("abc123");
		assertEquals(passChange, userLogin.getPassword());
		
		
		// Update User Info
		// Check token expired
		assertTrue(tokensService.isCheckTokenValid(userLogin.getAccess_token()));
		userLogin.setUsername("testUserUpdate");
		userLogin.setLastname("testUserUpdate");
		userLogin.setFirstname("testUserUpdate");
		userLogin.setModified_at(new Date());
		assertTrue(usersService.isUpdateUserInfo(userLogin));
		// Get return user
		Users getUserUpdate = usersService.getUserById(user.getId());
		// Check expected value matches actual value
		assertEquals("testUserUpdate", getUserUpdate.getUsername());
		assertEquals("testUserUpdate", getUserUpdate.getLastname());
		assertEquals("testUserUpdate", getUserUpdate.getFirstname());
		
						
		// Get User Info
		// Get userLogin info and assign to getUser1 obj
		Users getUser1 = usersService.getUserById(userLogin.getId());
		// Check expected value matches actual value
		assertEquals(userLogin.getId(), getUser1.getId());
		assertEquals(userLogin.getUsername(), getUser1.getUsername());
		assertEquals(userLogin.getPassword(), getUser1.getPassword());
		assertEquals(userLogin.getLastname(), getUser1.getLastname());
		assertEquals(userLogin.getFirstname(), getUser1.getFirstname());
		assertEquals(userLogin.getEmail(), getUser1.getEmail());
		assertEquals(userLogin.getCreated_at(), getUser1.getCreated_at());
//		assertEquals(userLogin.getModified_at(), getUser1.getModified_at());
		
		// Search user by name				
		String nameSearch = "test";
		List<Users> listUser = usersService.getListUserByName(nameSearch);
		// Check listuser contains user(s)
		assertTrue(listUser.size() > 0);
				
		// Logout
		assertTrue(tokensService.isDeleteTokenByUserId(userLogin.getId()));
		// Delete user
		assertTrue(usersService.isDeleteUser("testUserUpdate"));			
	}
	
	
//	public void delete()
//	{
//		usersService.isDeleteUser("testRegister");
//		usersService.isDeleteUser("testUserUpdate");
//	}
}
