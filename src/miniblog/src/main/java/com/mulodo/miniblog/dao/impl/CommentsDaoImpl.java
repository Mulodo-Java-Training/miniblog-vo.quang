package com.mulodo.miniblog.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mulodo.miniblog.dao.CommentsDao;
import com.mulodo.miniblog.model.Comments;
import com.mulodo.miniblog.model.Posts;

@Repository
public class CommentsDaoImpl implements CommentsDao 
{
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public boolean isAdd(Comments cmt) 
	{	
		Session session = sessionFactory.getCurrentSession();
		Transaction trans = session.beginTransaction();
		session.save(cmt);		
		trans.commit();
		return true;
	}
	
	@Override
	public Comments getCommentById(int id) 
	{	
		Session session = sessionFactory.getCurrentSession();
		Transaction trans = session.beginTransaction();
		Comments cmt = (Comments) session.get(Comments.class, id);		
		trans.commit();
		return cmt;
	}
	
	@Override
	public boolean isUpdate(Comments cmt) 
	{	
		Session session = sessionFactory.getCurrentSession();
		Transaction trans = session.beginTransaction();
		session.update(cmt);		
		trans.commit();
		return true;
	}

	@Override
	public boolean isDelete(Comments cmt) 
	{
		Session session = sessionFactory.getCurrentSession();
		Transaction trans = session.beginTransaction();
		session.delete(cmt);
		trans.commit();
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Comments> getCommentsByPostId(int post_id) 
	{
		Session session = sessionFactory.getCurrentSession();
		Transaction trans = session.beginTransaction();
		Criteria cr = session.createCriteria(Comments.class);
		List<Comments> listComment = cr.add(Restrictions.eq("post.id", post_id)).list();
		trans.commit();		
		return listComment;		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Comments> getCommentsByUserId(int user_id) 
	{
		Session session = sessionFactory.getCurrentSession();
		Transaction trans = session.beginTransaction();
		Criteria cr = session.createCriteria(Comments.class);
		List<Comments> listComment = cr.add(Restrictions.eq("user.id", user_id)).list();
		trans.commit();		
		return listComment;	
	}

}
