package com.mulodo.miniblog.service;

import java.util.List;

import com.mulodo.miniblog.model.Users;

public interface UsersService 
{	
	public boolean isValidateUser(Users user);
	public boolean isCheckUserExist(String username);
	public boolean isCheckEmailExist(String email);
	public boolean register(Users user);
	public boolean isLogin(String username, String password);
	public Users getUserByUsername(String username);
	public Users getUserById(int id);
	public boolean isUpdateUserInfo(Users user);
	public List<Users> getListUserByName(String name);
	public Users getUserByToken(String access_token);
	public Users getUserByUserId(int user_id);
	public boolean isDeleteUser(String username);
}
