package com.mulodo.miniblog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mulodo.miniblog.dao.PostsDao;
import com.mulodo.miniblog.model.Posts;
import com.mulodo.miniblog.service.PostsService;

@Service
public class PostsServiceImpl implements PostsService 
{
	@Autowired
	PostsDao postsdao;

	@Transactional
	public boolean createPost(Posts post) 
	{		
		if(postsdao.create(post) == true)
			return true;
		else		
			return false;
	}

	@Override
	public Posts getPostById(int id) 
	{		
		Posts post = postsdao.getPostById(id);
		if (post != null)
			return post;
		else
			return null;
	}

	@Override
	public boolean updatePost(Posts post) 
	{		
		if (postsdao.isUpdatePost(post) == true)
			return true;
		else
			return false;
	}

	@Override
	public boolean deletePost(int id) 
	{		
		if (postsdao.isDeletePost(id) == true)
			return true;
		else
			return false;
	}
	
	public List<Posts> getAllPosts() 
	{
		List<Posts> listPost = postsdao.getAllPost();
		if (listPost != null)
			return listPost;
		else
			return null;
	}

	@Override
	public List<Posts> getPostsForUser(int user_id) 
	{
		List<Posts> listPost = postsdao.getPostByUserId(user_id);
		if (listPost != null)
			return listPost;
		else
			return null;
	}

	@Override
	public List<Posts> get10LatestPost() 
	{
		List<Posts> listPost = postsdao.get10LatestPost();
		if (listPost != null)
			return listPost;
		else
			return null;
	}
}

