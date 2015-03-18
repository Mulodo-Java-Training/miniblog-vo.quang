package com.mulodo.miniblog.service;

import java.util.List;

import com.mulodo.miniblog.model.Tokens;
import com.mulodo.miniblog.model.Users;

public interface TokensService 
{
	public boolean isCreateToken(Users user);
	public boolean isDeleteToken(Tokens token);
	public boolean isDeleteTokenByUserId(int user_id);
	public Tokens searchToken(String access_token);
	public boolean isCheckTokenValid(String access_token);
	public List<Tokens> getTokenByUserId(int user_id);
}
