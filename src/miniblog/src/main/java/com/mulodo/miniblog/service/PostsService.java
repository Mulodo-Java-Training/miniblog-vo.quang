package com.mulodo.miniblog.service;

import com.mulodo.miniblog.model.Posts;

public interface PostsService {

	public boolean createPost(Posts post);
	public Posts GetPostById(int id);
	public boolean UpdatePost(Posts post);
	public boolean DeletePost(int id);
	
}
