package com.mulodo.miniblog.service;

import java.util.List;

import com.mulodo.miniblog.model.Tokens;
import com.mulodo.miniblog.model.Users;

public interface UsersService {
	
	public boolean ValidateUser(Users user);
	public boolean CheckUserExist(String username);
	public boolean CheckEmailExist(String email);
	public boolean Register(Users user);
	public boolean Login(String username, String password);
	public Users GetUserByUsername(String username);
	public Users GetUserById(int id);
	public boolean Logout(Tokens token);
	public boolean UpdateUserInfo (Users user);
	public List<Users> GetListUserByName(String name);
	public Users GetUserByToken(String access_token);
	
}
