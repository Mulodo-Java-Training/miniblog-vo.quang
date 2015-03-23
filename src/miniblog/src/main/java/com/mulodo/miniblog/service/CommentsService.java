package com.mulodo.miniblog.service;

import java.util.List;

import com.mulodo.miniblog.model.Comments;
import com.mulodo.miniblog.model.Posts;


public interface CommentsService 
{	
	public boolean isAddComment(Comments cmt);
	public boolean isUpdateComment(Comments cmt);
	public Comments getComment(int id);
	public boolean isDeleteComment(Comments cmt);
	public List<Comments> getCommentsByPostId(Posts post);
	public List<Comments> getCommentsByUserId(int user_id);
	
}
