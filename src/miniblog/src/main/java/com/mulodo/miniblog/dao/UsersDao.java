package com.mulodo.miniblog.dao;

import java.util.List;

import com.mulodo.miniblog.model.Users;

public interface UsersDao {
	
	public Users getUserByUsername(String username);
	public Users get_user_by_email(String email);
	public void addNewUser(Users user);
	public Users getUserById(int id);
	public Users getUserLogin(String username, String password);
	public boolean UpdateUserInfo(Users user);
	public List<Users> GetListUserByName(String name);
	public Users GetUserByAccessToken(String access_token);
}
