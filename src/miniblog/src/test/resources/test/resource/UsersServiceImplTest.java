package test.resource;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mulodo.miniblog.encryption.Encryption;
import com.mulodo.miniblog.model.Users;
import com.mulodo.miniblog.service.UsersService;

@Service
public class UsersServiceImplTest implements UsersService 
{

	@Override
	public boolean isValidateUser(Users user) 
	{
		if (user.getUsername().equals("testsuccess") || 
			user.getUsername().equals("abc") || 
			user.getUsername().equals("testUsername") ||
			user.getPassword().equals("123456"))
			return true;
		else
			return false;		
	}

	@Override
	public boolean isCheckUserExist(String username) 
	{
		if (username == "testusername")
			return true;
		else
			return false;
	}

	@Override
	public boolean isCheckEmailExist(String email) 
	{
		if (email == "testEmailExist@gmail.com")
			return true;
		return false;
	}

	@Override
	public boolean register(Users user) 
	{		
		if (user.getUsername() == "testsuccess" &&
			user.getPassword().equals(Encryption.hashSHA256("123456")) &&
			user.getLastname() == "test" &&
			user.getFirstname() == "test" &&
			user.getEmail() == "testsuccess@gmail.com" &&
			user.getImage() == "image.jpg")
			return true;
		else
			return false;
	}

	@Override
	public boolean isLogin(String username, String password) 
	{
		if (username == "abc" &&
			password.equals(Encryption.hashSHA256("123456"))) 
			return true;
		else
			return false;
	}

	@Override
	public Users getUserByUsername(String username) 
	{
		if (username == "abc") {
			Users user = new Users();
			user.setId(1);
			return user;
		}
		else
			return null;
	}

	@Override
	public Users getUserById(int id) 
	{
		if (id == 1) {
			Users user = new Users();
			user.setFirstname("test");
			user.setLastname("test");
			user.setUsername("test");
			user.setPassword(Encryption.hashSHA256("123456"));
			return user;
		}
		else	
			return null;
	}


	@Override
	public boolean isUpdateUserInfo(Users user) 
	{	
		if (user.getPassword() == "123456") {
			user.setPassword("abc123");
			return true;
		}
		
		if (user.getUsername() == "testUsername")
			return true;
		else		
			return false;
	}

	@Override
	public List<Users> getListUserByName(String name) 
	{
		if (name == "test") {
			List<Users> listUser = new ArrayList<Users>();
			Users user1 = new Users();
			user1.setId(1);
			user1.setUsername("test1");
			listUser.add(user1);
			Users user2 = new Users();
			user2.setId(2);
			user2.setUsername("test2");
			listUser.add(user2);
			return listUser;
		}
		return null;
	}

	@Override
	public Users getUserByToken(String access_token) 
	{
		if (access_token == "abc123xyz") {
			Users user = new Users();
			user.setId(1);
			return user;
		}
		else
			return null;		
	}

	@Override
	public boolean isDeleteUser(String username) {
		// TODO Auto-generated method stub
		return false;
	}

		
}
