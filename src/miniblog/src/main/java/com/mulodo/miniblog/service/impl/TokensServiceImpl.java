package com.mulodo.miniblog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mulodo.miniblog.dao.TokensDao;
import com.mulodo.miniblog.model.Tokens;
import com.mulodo.miniblog.service.TokensService;

@Service
public class TokensServiceImpl implements TokensService {

	@Autowired
	TokensDao tokensDao; 
	
	@Transactional
	public boolean CreateToken(Tokens token) {		
		if (tokensDao.Save(token) == true)
			return true;
		else
			return false;		
	}

	@Transactional
	public boolean DeleteToken(Tokens token) {
		if (tokensDao.Delete(token) == true)
			return true;
		else
			return false;
	}

	@Override
	public Tokens SearchToken(Tokens token) {
		return tokensDao.Search(token);
	}

	@Override
	public boolean CheckTokenValid(int user_id, String access_token) {
		if (tokensDao.CheckTokenValid(user_id, access_token) == true)
			return true;
		else
			return false;
				
	}

	@Override
	public List<Tokens> GetTokenByUserId(int user_id) {
		
		List<Tokens> listToken = tokensDao.GetTokenByUserId(user_id);
		if (listToken.size() != 0)						
			return listToken;
		else
			return null;
	}

}
