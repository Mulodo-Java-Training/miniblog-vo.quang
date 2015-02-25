package com.mulodo.miniblog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mulodo.miniblog.dao.TokensDao;
import com.mulodo.miniblog.dao.UsersDao;
import com.mulodo.miniblog.model.Tokens;
import com.mulodo.miniblog.model.Users;
import com.mulodo.miniblog.service.UsersService;

@Service
public class UsersServiceImpl implements UsersService 
{

	@Autowired
	private UsersDao usersdao;
	@Autowired
	private TokensDao tokensdao;
	
	@Transactional
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
	
	@Transactional
	public boolean register(Users user) 
	{		
		try {
			usersdao.addNewUser(user);
			if (user != null)
				return true;
			else
				return false;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}		
	}

	@Transactional
	public boolean isCheckUserExist(String username) 
	{		
		try {
			if (usersdao.getUserByUsername(username) != null)
				return true;
			else
				return false;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}		
	}

	@Transactional
	public boolean isCheckEmailExist(String email) 
	{	
		try {
			if (usersdao.get_user_by_email(email) != null)
				return true;
			else
				return false;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public boolean isLogin(String username, String password) 
	{		
		try {
			if (usersdao.getUserLogin(username, password) != null)
				return true;
			else
				return false;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}				
	}

	@Transactional
	public Users getUserByUsername(String username) 
	{		
		return usersdao.getUserByUsername(username);
	}

	
	@Transactional
	public Users getUserById(int id) 
	{	
		try {
			Users user = usersdao.getUserById(id);
			return user;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	@Transactional
	public boolean isLogout(Tokens token) 
	{
		try {
			tokensdao.isDelete(token);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}	
	}

	@Transactional
	public boolean isUpdateUserInfo(Users user) 
	{
		try {
			usersdao.isUpdateUserInfo(user);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}		
	}

	@Override
	public List<Users> getListUserByName(String name) 
	{
		try {
			List<Users> listUser = usersdao.getListUserByName(name);
			if (listUser != null)
				return listUser;
			else 
				return null;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Users getUserByToken(String access_token) 
	{
		try {
			Users user = usersdao.getUserByAccessToken(access_token);
			if (user != null)
				return user;
			else
				return null;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
	}

	@Override
	public boolean isDeleteUser(String username) 
	{
		try {
			if (usersdao.isDelete(username) == true)
				return true;
			else
				return false;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}	
}
