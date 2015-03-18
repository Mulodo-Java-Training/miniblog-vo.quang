package com.mulodo.miniblog.service;

import java.util.List;

import com.mulodo.miniblog.model.Comments;


public interface CommentsService 
{	
	public boolean isAddComment(Comments cmt);
	public boolean isUpdateComment(Comments cmt);
	public Comments getComment(int id);
	public boolean isDeleteComment(Comments cmt);
	public List<Comments> getCommentsByPostId(int post_id);
	public List<Comments> getCommentsByUserId(int user_id);
	
}
