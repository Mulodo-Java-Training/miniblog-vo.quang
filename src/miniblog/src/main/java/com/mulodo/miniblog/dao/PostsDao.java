package com.mulodo.miniblog.dao;

import java.util.List;

import com.mulodo.miniblog.model.Posts;
import com.mulodo.miniblog.model.Users;

public interface PostsDao 
{
	public boolean create(Posts post);
	public Posts getPostById(int id);
	public boolean isUpdatePost(Posts post);
	public boolean isDeletePost(int id);
	public List<Posts> getAllPost();
	public List<Posts> getPostByUserId(Users user);
	public List<Posts> get10LatestPost();
}
