package com.mulodo.miniblog.service;

import java.util.List;

import com.mulodo.miniblog.model.Tokens;

public interface TokensService {

	public boolean CreateToken (Tokens token);
	public boolean DeleteToken (Tokens token);
	public Tokens SearchToken (Tokens token);
	public boolean CheckTokenValid(int user_id, String access_token);
	public List<Tokens> GetTokenByUserId(int user_id);
}
