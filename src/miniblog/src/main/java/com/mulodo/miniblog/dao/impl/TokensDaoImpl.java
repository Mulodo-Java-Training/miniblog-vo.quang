package com.mulodo.miniblog.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mulodo.miniblog.dao.TokensDao;
import com.mulodo.miniblog.model.Tokens;
import com.mulodo.miniblog.model.Users;

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
	
	@Override
	public boolean isDeleteByUserId(int user_id) 
	{	
		Session session = sessionFactory.getCurrentSession();
		Transaction trans = session.beginTransaction();
		session.createQuery("DELETE FROM Tokens " + "WHERE user_id=:user_id").
		setParameter("user_id", user_id).executeUpdate();
		trans.commit();
		return true;	
	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public Tokens search(Tokens token) 
//	{		
//		Session session = sessionFactory.getCurrentSession();
//		Transaction trans = session.beginTransaction();
//		List<Tokens> result = session.createCriteria(Tokens.class)
// 				.add(Restrictions.like("access_token", token.getAccess_token())).list();	
// 		trans.commit();
//		return result.get(0);
//	}

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

	@SuppressWarnings("unchecked")
	@Override
	public Tokens getTokenByAccess_Token(String access_token) 
	{
		Session session = sessionFactory.getCurrentSession();
		Transaction trans = session.beginTransaction();
		Criteria cr = session.createCriteria(Tokens.class);
		List<Tokens> listToken = cr.add(Restrictions.eq("access_token", access_token)).list();
		trans.commit();
		if (listToken.size() != 0)
			return listToken.get(0);
		else
			return null;	
	}


}
