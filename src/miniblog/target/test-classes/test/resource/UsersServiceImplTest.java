package test.resource;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mulodo.miniblog.encryption.Encryption;
import com.mulodo.miniblog.model.Tokens;
import com.mulodo.miniblog.model.Users;
import com.mulodo.miniblog.service.UsersService;

@Service
public class UsersServiceImplTest implements UsersService 
{

	@Override
	public boolean isValidateUser(Users user) 
	{
		if (user.getUsername() != null && !user.getUsername().isEmpty() && 
				user.getUsername().length() >= 2 && user.getUsername().length() <= 45 &&
				user.getUsername().matches("[a-zA-Z0-9 ]*") &&
	
				user.getPassword() != null && !user.getPassword().isEmpty() && 
				user.getPassword().length() >= 6 && user.getPassword().length() <= 45 &&
				user.getPassword().matches("[a-zA-Z0-9 ]*") &&
			
				user.getLastname() != null && !user.getLastname().isEmpty() && 
				user.getFirstname() != null && !user.getFirstname().isEmpty() && 
				user.getFirstname().matches("[a-zA-Z0-9 ]*") &&
			
				user.getEmail() != null && !user.getFirstname().isEmpty() &&
				user.getEmail().matches("^[\\w-_+]*[\\.]?[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$"))
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
	public boolean isLogout(Tokens token) 
	{
		if (token.getAccess_token().equals("abc123xyz"))
			return true;
		else			
			return false;
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
	public List<Users> getListUserByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Users getUserByToken(String access_token) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isDeleteUser(String username) {
		// TODO Auto-generated method stub
		return false;
	}

		
}
