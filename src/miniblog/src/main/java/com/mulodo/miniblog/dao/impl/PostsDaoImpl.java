package com.mulodo.miniblog.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mulodo.miniblog.dao.PostsDao;
import com.mulodo.miniblog.model.Posts;

@Repository
public class PostsDaoImpl implements PostsDao {

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public boolean create(Posts post) {
		
		Session session = sessionFactory.getCurrentSession();
		Transaction trans = session.beginTransaction();
		session.save(post);		
		trans.commit();
		return true;
	}

	@Override
	public Posts GetPostById(int id) {

		Session session = sessionFactory.getCurrentSession();
		Transaction trans = session.beginTransaction();
		Posts post = (Posts) session.get(Posts.class, id);
		trans.commit();
		return post;
	}

	@Override
	public boolean UpdatePost(Posts post) {
		
		Session session = sessionFactory.getCurrentSession();
		Transaction trans = session.beginTransaction();
		session.update(post);
		trans.commit();
		return true;
	}

	@Override
	public boolean DeletePost(int id) {
		
		Session session = sessionFactory.getCurrentSession();
		Transaction trans = session.beginTransaction();
//		session.delete(GetPostById(id));
		Posts post = (Posts) session.load(Posts.class, id);
		session.delete(post);
		trans.commit();
		return true;
	}
	
	
	
}
