package com.mulodo.miniblog.service;

import java.util.List;

import com.mulodo.miniblog.model.Posts;

public interface PostsService 
{
	public boolean createPost(Posts post);
	public Posts getPostById(int id);
	public boolean updatePost(Posts post);
	public boolean deletePost(int id);
	public List<Posts> getAllPosts();
	public List<Posts> getPostsForUser(int user_id);
	public List<Posts> get10LatestPost();
	
}
