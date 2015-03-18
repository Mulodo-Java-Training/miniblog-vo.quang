package com.mulodo.miniblog.service.impl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mulodo.miniblog.dao.TokensDao;
import com.mulodo.miniblog.encryption.Encryption;
import com.mulodo.miniblog.model.Tokens;
import com.mulodo.miniblog.model.Users;
import com.mulodo.miniblog.service.TokensService;

@Service
public class TokensServiceImpl implements TokensService 
{
	@Autowired
	TokensDao tokensDao; 
	
	@Transactional
	public boolean isCreateToken(Users user) 
	{	
		try {
			// Create access_token when create account success.   
			String access_token = Encryption.createToken(user.getId());
			// Set access_token to user 
			user.setAccess_token(access_token);
			// Create token 
			Tokens token = new Tokens();
			// Current time
			token.setCreated_at(new Timestamp(System.currentTimeMillis()));
			// Expired in 7 days
			token.setExpired(new Timestamp(System.currentTimeMillis() + 7*24*60*60*1000));
			token.setAccess_token(access_token);
			token.setUser_id(user.getId());						
			if (tokensDao.isSave(token) == true)
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
	public boolean isDeleteToken(Tokens token) 
	{
		try {
			if (tokensDao.isDelete(token) == true)
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
	public boolean isDeleteTokenByUserId(int user_id) 
	{
		try {
			if (tokensDao.isDeleteByUserId(user_id) == true)
				return true;
			else
				return false;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}		
	}

	@Override
	public Tokens searchToken(String access_token) 
	{
		try {
			Tokens tokenSearch = tokensDao.getTokenByAccess_Token(access_token);
			if (tokenSearch != null)
				return tokenSearch;
			else
				return null;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean isCheckTokenValid( String access_token) 
	{
		try {
			Tokens token = tokensDao.getTokenByAccess_Token(access_token);
			if (token != null) {
				// return positive number: expired date < current date
				if (checkExpireTime(token.getExpired())>=0) {
					//tokensDao.isDelete(token);
					return false;
				}
				else
					return true;  
			}
			else
				return false;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}		
	}
	
	//Check token current date greater than expired date
	private int checkExpireTime(Date expire)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(expire);
		return Calendar.getInstance().compareTo(cal);
	}

	@Override
	public List<Tokens> getTokenByUserId(int user_id) 
	{	
		try {
			List<Tokens> listToken = tokensDao.getTokenByUserId(user_id);
			if (listToken.size() != 0)						
				return listToken;
			else
				return null;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
	}
}
