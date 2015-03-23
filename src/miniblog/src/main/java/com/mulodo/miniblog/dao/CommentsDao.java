package com.mulodo.miniblog.dao;

import java.util.List;

import com.mulodo.miniblog.model.Comments;
import com.mulodo.miniblog.model.Posts;


public interface CommentsDao 
{	
	public boolean isAdd(Comments cmt); 
	public boolean isUpdate(Comments cmt); 
	public Comments getCommentById(int id);
	public boolean isDelete(Comments cmt);
	public List<Comments> getCommentsByPostId(int post_id);
	public List<Comments> getCommentsByUserId(int user_id);
}
