package com.mulodo.miniblog.service;

import java.util.List;

import com.mulodo.miniblog.model.Tokens;

public interface TokensService 
{
	public boolean isCreateToken(Tokens token);
	public boolean isDeleteToken(Tokens token);
	public Tokens searchToken(String access_token);
	public boolean isCheckTokenValid(String access_token);
	public List<Tokens> getTokenByUserId(int user_id);
}
