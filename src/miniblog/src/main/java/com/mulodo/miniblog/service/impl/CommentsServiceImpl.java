package com.mulodo.miniblog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mulodo.miniblog.dao.CommentsDao;
import com.mulodo.miniblog.model.Comments;
import com.mulodo.miniblog.service.CommentsService;

@Service
public class CommentsServiceImpl implements CommentsService 
{
	@Autowired
	CommentsDao commentsDao;

	@Transactional
	public boolean isAddComment(Comments cmt) 
	{
		if(commentsDao.isAdd(cmt) == true)
			return true;
		else
			return false;		
	}
	
	@Transactional
	public Comments getComment(int id) 
	{
		Comments cmt = commentsDao.getCommentById(id);
		if (cmt != null)
			return cmt;
		else
			return null;
	}

	@Transactional
	public boolean isUpdateComment(Comments cmt) 
	{
		if (commentsDao.isUpdate(cmt) == true)
			return true;
		else
			return false;
	}

	@Transactional
	public boolean isDeleteComment(Comments cmt) 
	{	
		if (commentsDao.isDelete(cmt) == true)
			return true;
		else
			return false;
	}

	@Transactional
	public List<Comments> getCommentsByPostId(int post_id)
	{
		List<Comments> listComment = commentsDao.getCommentsByPostId(post_id);
		if (listComment.size() > 0)
			return listComment;
		else
			return null;
	}

	@Override
	public List<Comments> getCommentsByUserId(int user_id) 
	{
		List<Comments> listComment = commentsDao.getCommentsByUserId(user_id);
		if (listComment.size() > 0)
			return listComment;
		else
			return null;
	}

}

