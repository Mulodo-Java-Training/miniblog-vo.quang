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

import com.mulodo.miniblog.dao.PostsDao;
import com.mulodo.miniblog.model.Posts;
import com.mulodo.miniblog.model.Users;

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
	public List<Posts> getPostByUserId(Users user) 
	{
		Session session = sessionFactory.getCurrentSession();
		Transaction trans = session.beginTransaction();
		try {
			Criteria cr = session.createCriteria(Posts.class);
			List<Posts> listPost = cr.add(Restrictions.eq("user.id", user.getId())).list();
			trans.commit();
			return listPost;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
//	@SuppressWarnings({ "unchecked" })
//	@Override
//	public List<Posts> getPostByUsername(String username) 
//	{
//		Session session = sessionFactory.getCurrentSession();
//		Transaction trans = session.beginTransaction();
//		 
//		Query query = session.createQuery("from Posts p where p.user.username = :username").setParameter("username", username);
//		List<Posts> listPost = query.list();
//		trans.commit();
//		return listPost;
//	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Posts> get10LatestPost() 
	{
		Session session = sessionFactory.getCurrentSession();
		Transaction trans = session.beginTransaction();
		String queryStr = "from Posts order by created_at desc";
		Query query = session.createQuery(queryStr).setMaxResults(10);
		List<Posts> listPost = query.list();
		trans.commit();
		return listPost;
	}	
}
