package com.mulodo.miniblog.dao;

import java.util.List;

import com.mulodo.miniblog.model.Tokens;


public interface TokensDao {

	public boolean Save(Tokens token);
	public boolean Delete(Tokens token);
	public Tokens Search(Tokens token);
	public boolean CheckTokenValid(int user_id, String access_token);
	public List<Tokens> GetTokenByUserId(int user_id);
	//public Tokens GetTokenByAccess_Token(String access_token);
}
