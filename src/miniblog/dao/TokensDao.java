package com.mulodo.miniblog.dao;

import java.util.List;

import com.mulodo.miniblog.model.Tokens;

public interface TokensDao 
{
	public boolean isSave(Tokens token);
	public boolean isDelete(Tokens token);
	public Tokens search(Tokens token);
	public List<Tokens> getTokenByUserId(int user_id);
	public Tokens getTokenByAccess_Token(String access_token);
}
