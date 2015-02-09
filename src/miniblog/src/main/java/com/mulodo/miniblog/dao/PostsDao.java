package com.mulodo.miniblog.dao;

import com.mulodo.miniblog.model.Posts;

public interface PostsDao {

	public boolean create(Posts post);
	public Posts GetPostById(int id);
	public boolean UpdatePost(Posts post);
	public boolean DeletePost(int id);
	
}
