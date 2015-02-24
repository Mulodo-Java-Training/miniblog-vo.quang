package com.mulodo.miniblog.controller;

import java.util.Calendar;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.mulodo.miniblog.model.Posts;
import com.mulodo.miniblog.model.Users;
import com.mulodo.miniblog.responseformat.ResponseFormat;
import com.mulodo.miniblog.service.PostsService;
import com.mulodo.miniblog.service.TokensService;
import com.mulodo.miniblog.service.UsersService;

@Path("v1/posts")
@Controller
public class PostsController 
{
	@Autowired
	PostsService postsService;
	@Autowired
	UsersService usersService;
	@Autowired
	TokensService tokensService;
	
	@POST
	@Path("create")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createPost(Posts data, @HeaderParam("access_token") String access_token) 
	{
		ResponseFormat rf = new ResponseFormat();
		
		// Check input
		if (data.getTitle() != null && data.getTitle().matches(".*\\w.*") && 
			data.getDescription() != null && data.getDescription().matches(".*\\w.*") &&
			data.getContent() != null && data.getContent().matches(".*\\w.*")) {
						
			Posts post = new Posts();
			post.setTitle(data.getTitle());
			post.setDescription(data.getDescription());
			post.setContent(data.getContent());
			post.setImage(data.getImage());
			post.setStatus(true);
			post.setCreated_at(Calendar.getInstance().getTime());
			post.setModified_at(Calendar.getInstance().getTime());
			
			if (access_token != null) {
				// Check whether token expired 
				if (tokensService.isCheckTokenValid(access_token) == true) {
					// Set user who create post
					post.setUser_id(usersService.getUserByToken(access_token).getId());
					// Check whether create post success
					if (postsService.createPost(post) == true) {			
						rf.meta.id = 200;
						rf.meta.message = "New post is created success";
						rf.data = post;
						return Response.status(200).entity(rf).build();
					}
					else {
						rf.meta.id = 9001;
						rf.meta.message = "Error";
						return Response.status(9001).entity(rf).build();
					}					
				}
				else {
					rf.meta.id = 1002;
					rf.meta.message = "Access token has expired.";
					return Response.status(1002).entity(rf).build();
				}	
			}
			else {
				rf.meta.id = 9002;
				rf.meta.message = "Missing token.Please login.";
				return Response.status(9002).entity(rf).build();
			}							
		}
		else {
			rf.meta.id = 1001;
			rf.meta.message = "Input invalid";
			return Response.status(1001).entity(rf).build();		
		}
	}
		
	
	@PUT
	@Path("{id}/active")
	@Produces(MediaType.APPLICATION_JSON)
	public Response activePostStatus(@PathParam("id") int id, @HeaderParam("access_token") String access_token) 
	{
		ResponseFormat rf = new ResponseFormat();
		
		// Get post by post id
		Posts post = new Posts();
		post = postsService.getPostById(id);
		if (post != null) {
			// Check token null
			if (access_token != null) {
				// Check whether token expired 
				if (tokensService.isCheckTokenValid(access_token) == true) {
					// Get user by access token
					Users user = usersService.getUserByToken(access_token);
					// Check whether access_token is valid
					if (user != null) {
						// Check whether post belongs to right user
						if (post.getUser_id() == user.getId()) {
							post.setStatus(true);
							// Update post
							if (postsService.updatePost(post) == true) {
								rf.meta.id = 200;
								rf.meta.message = "Post's status updated success";
								rf.data = "Post id: " + post.getId() + "  " + "Status: " + post.isStatus();
								return Response.status(200).entity(rf).build();
							}
							else {
								rf.meta.id = 9001;
								rf.meta.message = "Error.";
								return Response.status(9001).entity(rf).build();
							}					
						}
						else {
							rf.meta.id = 2505;
							rf.meta.message = "This post does not belong to the user with id: " + user.getId();
							return Response.status(2505).entity(rf).build();
						}			
					}
					else {
						rf.meta.id = 1003;
						rf.meta.message = "Access token invalid";
						return Response.status(1003).entity(rf).build();
					}			
				}
				else {
					rf.meta.id = 1002;
					rf.meta.message = "Access token has expired.";
					return Response.status(1002).entity(rf).build();
				}
			} 
			else {
				rf.meta.id = 9002;
				rf.meta.message = "Missing access token. Please login.";
				return Response.status(9002).entity(rf).build();
			}	
		}
		else {
			rf.meta.id = 2504;
			rf.meta.message = "Post is not existed.";
			return Response.status(2504).entity(rf).build();
		}
	}
	
	@PUT
	@Path("{id}/deactive")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deactivePostStatus(@PathParam("id") int id, @HeaderParam("access_token") String access_token) 
	{
		ResponseFormat rf = new ResponseFormat();
				
		Posts post = new Posts();
		// Get post by post id
		post = postsService.getPostById(id);
		
		//Check post existed
		if (post != null) {
			// Check token null
			if (access_token != null) {
				// Check whether token has expired or not
				if (tokensService.isCheckTokenValid(access_token) == true) {
					// Get user by access token
					Users user = usersService.getUserByToken(access_token);
					// Check whether access_token is valid
					if (user != null) {
						// Check whether post belongs to right user
						if (post.getUser_id() == user.getId()) {
							post.setStatus(false);
							// Update post
							if (postsService.updatePost(post) == true) {
								rf.meta.id = 200;
								rf.meta.message = "Post's status updated success";
								rf.data = "Post id: " + post.getId() + "  " + "Status: " + post.isStatus();
								return Response.status(200).entity(rf).build();
							}
							else {
								rf.meta.id = 9001;
								rf.meta.message = "Error";
								return Response.status(9001).entity(rf).build();
							}					
						}
						else {
							rf.meta.id = 2505;
							rf.meta.message = "This post does not belong to the user with id: " + user.getId();
							return Response.status(2505).entity(rf).build();
						}			
					}
					else {
						rf.meta.id = 1003;
						rf.meta.message = "Access token invalid";
						return Response.status(1003).entity(rf).build();
					}			
				}
				else {
					rf.meta.id = 1002;
					rf.meta.message = "Access token has expired.";
					return Response.status(1002).entity(rf).build();
				}
			} 
			else {
				rf.meta.id = 9002;
				rf.meta.message = "Missing access token. Please login.";
				return Response.status(9002).entity(rf).build();
			}
		}
		else {
			rf.meta.id = 2504;
			rf.meta.message = "Post is not existed.";
			return Response.status(2504).entity(rf).build();
		}
		
	}

	@PUT
	@Path("{id}/edit")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editPost(@PathParam("id") int id, Posts data, @HeaderParam("access_token") String access_token) 
	{
		ResponseFormat rf = new ResponseFormat();
		
		Posts post = new Posts();
		// Get post by post id
		post = postsService.getPostById(id);
		
		// Check post existed
		if (post != null) {
			// Check input
			if (data.getTitle() != null && data.getTitle().matches(".*\\w.*") &&
				data.getDescription() != null && data.getDescription().matches(".*\\w.*") &&
				data.getContent() != null && data.getContent().matches(".*\\w.*")) {
				
				// Check token null
				if (access_token != null) {
					// Check whether token has expired or not
					if (tokensService.isCheckTokenValid(access_token) == true) {
						// Get user by access token
						Users user = usersService.getUserByToken(access_token);
						// Check token valid
						if (user != null) {
							// Check whether post belongs to right user
							if (post.getUser_id() == user.getId()) {
								// Set post object by data
								post.setTitle(data.getTitle());
								post.setDescription(data.getDescription());
								post.setContent(data.getContent());
								post.setImage(data.getImage());
								post.setModified_at(Calendar.getInstance().getTime());
								// Update post
								if (postsService.updatePost(post) == true) {
									rf.meta.id = 200;
									rf.meta.message = "Post updated success";
									rf.data = post;
									return Response.status(200).entity(rf).build();
								}
								else {
									rf.meta.id = 9001;
									rf.meta.message = "Error.";
									return Response.status(9001).entity(rf).build();
								}					
							}
							else {
								rf.meta.id = 2505;
								rf.meta.message = "This post does not belong to the user with id: " + user.getId();
								return Response.status(2505).entity(rf).build();
							}
						}
						else {
							rf.meta.id = 1003;
							rf.meta.message = "Access token invalid.";
							return Response.status(1003).entity(rf).build();
						}
					}
					else {
						rf.meta.id = 1002;
						rf.meta.message = "Access token has expired.";
						return Response.status(1002).entity(rf).build();
					}
				}
				else {
					rf.meta.id = 9002;
					rf.meta.message = "Missing token. Please login.";
					return Response.status(9002).entity(rf).build();
				}
			}
			else {
				rf.meta.id = 1001;
				rf.meta.message = "Input failed.";
				return Response.status(1001).entity(rf).build();
			}
		}
		else {
			rf.meta.id = 2504;
			rf.meta.message = "Post is not existed.";
			return Response.status(2504).entity(rf).build();
		}
	}
	
	@DELETE
	@Path("{id}/delete")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deletePost(@PathParam("id") int id, @HeaderParam("access_token") String access_token) 
	{
		ResponseFormat rf = new ResponseFormat();
		
		// Get post by id
		Posts post = postsService.getPostById(id);
		// Check whether this post is existed or not
		if (post != null) {
			if (access_token != null) {
				// Check whether access_token expired
				if (tokensService.isCheckTokenValid(access_token) == true) {
					// Get user by access token
					Users user = usersService.getUserByToken(access_token);
					// Check token valid
					if (user != null) {
						// Check whether post belongs to right user
						if (post.getUser_id() == user.getId()) {
							if (postsService.deletePost(id) == true) {
								rf.meta.id = 200;
								rf.meta.message = "Post deleted success.";
								return Response.status(200).entity(rf).build();
							}
							else {
								rf.meta.id = 9001;
								rf.meta.message = "Error.";
								return Response.status(9001).entity(rf).build();
							}
						}
						else {
							rf.meta.id = 2505;
							rf.meta.message = "This post does not belong to the user with id: " + user.getId();
							return Response.status(2505).entity(rf).build();
						}	
					}
					else {
						rf.meta.id = 1003;
						rf.meta.message = "Access token invalid";
						return Response.status(1003).entity(rf).build();
					}
				}
				else {
					rf.meta.id = 1002;
					rf.meta.message = "Access token has expired.";
					return Response.status(1002).entity(rf).build();
				}							
			}
			else {
				rf.meta.id = 9002;
				rf.meta.message = "Missing token. Please login.";
				return Response.status(9002).entity(rf).build();
			}
		}
		else {
			rf.meta.id = 2504;
			rf.meta.message = "Post is not existed.";
			return Response.status(2504).entity(rf).build();
		}
	}
	
	@GET
	@Path("getall")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllPosts() 
	{
		ResponseFormat rf= new ResponseFormat();
		
		List<Posts> listPost = postsService.getAllPosts();
		if (listPost != null) {
			rf.meta.id = 200;
			rf.meta.message = "Post searched sucess.";
			rf.data = listPost;
			return Response.status(200).entity(rf).build();
		}
		else {
			rf.meta.id = 9001;
			rf.meta.message = "Error.";
			return Response.status(9001).entity(rf).build();
		}
	}
	
	@GET
	@Path("getpostforuser")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPostforUser(@HeaderParam("access_token") String access_token) 
	{
		ResponseFormat rf = new ResponseFormat();
		
		// Check token null
		if (access_token != null) {
			// Check whether token expired or not 
			if (tokensService.isCheckTokenValid(access_token) == true) {
				Users user = usersService.getUserByToken(access_token);
				// Check token valid
				if (user != null) {
					List<Posts> listPost = postsService.getPostsForUser(user.getId());
					if (listPost != null) {
						rf.meta.id = 200;
						rf.meta.message = "Post for user searched success";
						rf.data = listPost;
						return Response.status(200).entity(rf).build();
					}
					else {
						rf.meta.id = 9001;
						rf.meta.message = "Error.";
						return Response.status(9001).entity(rf).build();
					}
				}
				else {
					rf.meta.id = 1003;
					rf.meta.message = "Access token invalid";
					return Response.status(1003).entity(rf).build();
				}
			}
			else {
				rf.meta.id = 1002;
				rf.meta.message = "Access token has expired.";
				return Response.status(1002).entity(rf).build();
			}
		}
		else {
			rf.meta.id = 9002;
			rf.meta.message = "Missing token. Please login.";
			return Response.status(9002).entity(rf).build();
		}	
	}
}
