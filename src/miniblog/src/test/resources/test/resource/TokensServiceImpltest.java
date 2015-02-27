package test.resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mulodo.miniblog.model.Tokens;
import com.mulodo.miniblog.model.Users;
import com.mulodo.miniblog.service.TokensService;

@Service
public class TokensServiceImpltest implements TokensService 
{

	@Override
	public boolean isCreateToken(Users user) 
	{
//		token.setAccess_token("abc123xyz");
//		return true;
//		if (token.getAccess_token() == "abc123xyz")
//			return true;
//		else
//			return false;
		
		user.setAccess_token("abc123xyz");
		return true;
	}

	@Override
	public boolean isDeleteToken(Tokens token) 
	{
		if (token.getAccess_token() == "abc123xyz")
			return true;
		else
			return false;
	}

	@Override
	public Tokens searchToken(String access_token) 
	{			
		if (access_token == "abc123xyz") {
			Tokens token = new Tokens();
			token.setId(1);
			token.setAccess_token("abc123xyz");
			return token;
		}
		else
			return null;
	}

	@Override
	public boolean isCheckTokenValid(String access_token) 
	{
		if (access_token == "abc123xyz")
			return true;
		else
			return false;
	}

	@Override
	public List<Tokens> getTokenByUserId(int user_id) 
	{
		if (user_id == 1) {
			List<Tokens> listToken = new ArrayList<Tokens>();
			Tokens token1 = new Tokens();
			token1.setAccess_token("abc123xyz");
			token1.setId(1);
			token1.setCreated_at(new Date());
			listToken.add(token1);
			return listToken;
		}
		else
			return null;
	}

	@Override
	public boolean isDeleteTokenByUserId(int user_id) 
	{	
		if (user_id == 1)
			return true;
		else
			return false;			
	}

}
