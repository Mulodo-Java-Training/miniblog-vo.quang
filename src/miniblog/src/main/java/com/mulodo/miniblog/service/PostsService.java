package com.mulodo.miniblog.service;

import java.util.List;

import com.mulodo.miniblog.model.Posts;

public interface PostsService 
{
	public boolean createPost(Posts post);
	public Posts GetPostById(int id);
	public boolean UpdatePost(Posts post);
	public boolean DeletePost(int id);
	public List<Posts> getAllPosts();
	
}
