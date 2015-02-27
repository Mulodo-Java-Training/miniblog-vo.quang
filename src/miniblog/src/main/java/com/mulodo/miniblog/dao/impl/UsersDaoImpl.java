package com.mulodo.miniblog.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mulodo.miniblog.dao.UsersDao;
import com.mulodo.miniblog.model.Tokens;
import com.mulodo.miniblog.model.Users;

@Repository
public class UsersDaoImpl implements UsersDao 
{
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void addNewUser(Users user) 
	{		
		Session session = sessionFactory.openSession();
		Transaction trans = session.beginTransaction();
		session.save(user);
		trans.commit();	 
	}

	@Override
	public Users getUserById(int id) 
	{				
		Session session = sessionFactory.openSession();
		Transaction trans = session.beginTransaction();
		Users user = (Users) session.get(Users.class,id);
		trans.commit();	
		return user;		
	}

	@SuppressWarnings({ "unused", "unchecked" })
	@Override
	public Users getUserByUsername(String username) 
	{		
		Session session = sessionFactory.openSession();
		Transaction trans = session.beginTransaction();
		Criteria cr = session.createCriteria(Users.class);
		cr.add(Restrictions.eq("username", username));
		List<Users> listUser = cr.list();
		if (listUser.size() == 1)
			return listUser.get(0);
		else
			return null;									
	}

	@SuppressWarnings({ "unused", "unchecked" })
	@Override
	public Users get_user_by_email(String email) 
	{		
		Session session = sessionFactory.openSession();
		Transaction trans = session.beginTransaction();
		Criteria cr = session.createCriteria(Users.class);
		cr.add(Restrictions.eq("email", email));
		List<Users> listUser = cr.list();
		if (listUser.size() == 1)
			return listUser.get(0);	
		else
			return null;							
	}
	
	@SuppressWarnings({ "unused", "unchecked" })
	@Override
	public Users getUserLogin(String username, String password) 
	{		
		Session session = sessionFactory.openSession();
		Transaction trans = session.beginTransaction();
		Criteria cr = session.createCriteria(Users.class);
		cr.add(Restrictions.eq("username", username));
		cr.add(Restrictions.eq("password", password));
		List<Users> listUser = cr.list();
		if (listUser.size() == 1) {
			session.close();
			return listUser.get(0);
		}
		else {
			return null;
		}
	}

	@Override
	public boolean isUpdateUserInfo(Users user) 
	{		
		Session session = sessionFactory.getCurrentSession();
		Transaction trans = session.beginTransaction();
		session.update(user);
		trans.commit();
		return true;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<Users> getListUserByName(String name) 
	{
		String rename = "%" + name + "%";
		Session session = sessionFactory.getCurrentSession();
		Transaction trans = session.beginTransaction();
		Criteria cr = session.createCriteria(Users.class);
		List<Users> listUser = cr.add(Restrictions.or(Restrictions.ilike("firstname", rename), 
				Restrictions.ilike("lastname", rename))).list();				
		trans.commit();
		return listUser;
	}

	@Override
	public Users getUserByAccessToken(String access_token) 
	{
		Session session = sessionFactory.getCurrentSession();
		Transaction trans = session.beginTransaction();
		// Get token by access_token
		Criteria cr = session.createCriteria(Tokens.class);
		cr.add(Restrictions.eq("access_token", access_token));
		Tokens token = (Tokens) cr.list().get(0);
		if (token != null) {
			// Get user by token
			Criteria cr1 = session.createCriteria(Users.class);
			cr1.add(Restrictions.eq("id", token.getUser_id()));
			Users user = (Users) cr1.list().get(0);
			trans.commit();
			return user;
		}
		else {
			return null;
		}		
	}

	@Override
	public boolean isDelete(String username) 
	{
		Session session = sessionFactory.getCurrentSession();
		Transaction trans = session.beginTransaction();
		Users user = getUserByUsername(username);
		session.delete(user);
		trans.commit();
		return true;
	}
}
