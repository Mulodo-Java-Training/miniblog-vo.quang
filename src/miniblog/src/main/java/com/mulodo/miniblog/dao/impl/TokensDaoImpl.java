package com.mulodo.miniblog.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mulodo.miniblog.dao.TokensDao;
import com.mulodo.miniblog.model.Tokens;

@Repository
public class TokensDaoImpl implements TokensDao 
{
	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public boolean isSave(Tokens token) 
	{		
		Session session = sessionFactory.getCurrentSession();		
		Transaction trans = session.beginTransaction();
		session.save(token);
		trans.commit();
		return true;		
	}

	@Override
	public boolean isDelete(Tokens token) 
	{		
		Session session = sessionFactory.getCurrentSession();
		Transaction trans = session.beginTransaction();
		session.delete(token);
		trans.commit();		
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Tokens search(Tokens token) 
	{		
		Session session = sessionFactory.getCurrentSession();
		Transaction trans = session.beginTransaction();
		List<Tokens> result = session.createCriteria(Tokens.class)
 				.add(Restrictions.like("access_token", token.getAccess_token())).list();	
 		trans.commit();
		return result.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tokens> getTokenByUserId(int user_id) 
	{		
		Session session = sessionFactory.getCurrentSession();
		Transaction trans = session.beginTransaction();
		Criteria cr = session.createCriteria(Tokens.class);
		cr.add(Restrictions.eq("user_id", user_id));
		List<Tokens> listToken = cr.list();
		trans.commit();
		return listToken;
	}

	@Override
	public Tokens getTokenByAccess_Token(String access_token) 
	{
		Session session = sessionFactory.getCurrentSession();
		Transaction trans = session.beginTransaction();
		Criteria cr = session.createCriteria(Tokens.class);
		cr.add(Restrictions.eq("access_token", access_token));
		Tokens token = (Tokens) cr.list().get(0);
		trans.commit();
		return token;
	}


}
