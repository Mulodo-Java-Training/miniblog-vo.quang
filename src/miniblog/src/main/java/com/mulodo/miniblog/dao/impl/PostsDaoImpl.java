package com.mulodo.miniblog.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mulodo.miniblog.dao.PostsDao;
import com.mulodo.miniblog.model.Posts;

@Repository
public class PostsDaoImpl implements PostsDao 
{
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public boolean create(Posts post) 
	{	
		Session session = sessionFactory.getCurrentSession();
		Transaction trans = session.beginTransaction();
		session.save(post);		
		trans.commit();
		return true;
	}

	@Override
	public Posts getPostById(int id) 
	{
		Session session = sessionFactory.getCurrentSession();
		Transaction trans = session.beginTransaction();
		Posts post = (Posts) session.get(Posts.class, id);
		trans.commit();
		return post;
	}

	@Override
	public boolean isUpdatePost(Posts post) 
	{	
		Session session = sessionFactory.getCurrentSession();
		Transaction trans = session.beginTransaction();
		session.update(post);
		trans.commit();
		return true;
	}

	@Override
	public boolean isDeletePost(int id) 
	{		
		Session session = sessionFactory.getCurrentSession();
		Transaction trans = session.beginTransaction();
//		session.delete(GetPostById(id));
		Posts post = (Posts) session.load(Posts.class, id);
		session.delete(post);
		trans.commit();
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Posts> getAllPost() 
	{		
		Session session = sessionFactory.getCurrentSession();
		Transaction trans = session.beginTransaction();
		List<Posts> listPost = session.createQuery("from Posts").list();
		trans.commit();
		if (listPost != null)
			return listPost;
		else
			return null;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<Posts> getPostByUserId(int user_id) 
	{
		Session session = sessionFactory.getCurrentSession();
		Transaction trans = session.beginTransaction();
		Criteria cr = session.createCriteria(Posts.class);
		List<Posts> listPost = cr.add(Restrictions.eq("user_id", user_id)).list();
		trans.commit();
		return listPost;
	}
	
	
	
}
