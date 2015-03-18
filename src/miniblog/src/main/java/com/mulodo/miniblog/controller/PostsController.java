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

import com.mulodo.miniblog.constants.Constants;
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
//		if (data.getTitle() != null && data.getTitle().matches(Constants.REGEX_WHITE_SPACE) && 
//			data.getDescription() != null && data.getDescription().matches(Constants.REGEX_WHITE_SPACE) &&
//			data.getContent() != null && data.getContent().matches(Constants.REGEX_WHITE_SPACE)) {
		if (data.getTitle() != null &&  !data.getTitle().isEmpty() &&
				data.getDescription() != null  && !data.getDescription().isEmpty() &&
				data.getContent() != null && !data.getContent().isEmpty()) {		
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
					//post.setUser_id(usersService.getUserByToken(access_token).getId());
					post.setUsername(usersService.getUserByToken(access_token).getUsername());
					post.setUser(usersService.getUserByToken(access_token));
					// Check whether create post success
					if (postsService.createPost(post) == true) {			
						rf.meta.id = Constants.CODE_200;
						rf.meta.message = Constants.POST_CREATE_SUCCESS;
						rf.data = post;
						return Response.status(Constants.CODE_200).entity(rf).build();
					}
					else {
						rf.meta.id = Constants.CODE_9001;
						rf.meta.message = Constants.ERROR_MESSAGE;
						return Response.status(Constants.CODE_9001).entity(rf).build();
					}					
				}
				else {
					rf.meta.id = Constants.CODE_1002;
					rf.meta.message = Constants.TOKEN_EXPIRED;
					return Response.status(Constants.CODE_1002).entity(rf).build();
				}	
			}
			else {
				rf.meta.id = Constants.CODE_9002;
				rf.meta.message = Constants.TOKEN_MISSING;
				return Response.status(Constants.CODE_9002).entity(rf).build();
			}							
		}
		else {
			rf.meta.id = Constants.CODE_1001;
			rf.meta.message = Constants.INPUT_FAILED;
			return Response.status(Constants.CODE_1001).entity(rf).build();		
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
//						if (post.getUser_id() == user.getId()) {
						if (post.getUser().getId() == user.getId()) {
							post.setStatus(true);
							post.setUsername(user.getUsername());
							// Update post
							if (postsService.updatePost(post) == true) {
								rf.meta.id = Constants.CODE_200;
								rf.meta.message = Constants.POST_ACTIVE_SUCCESS;
								rf.data = "Post id: " + post.getId() + "  " + "Status: " + post.isStatus();
								return Response.status(Constants.CODE_200).entity(rf).build();
							}
							else {
								rf.meta.id = Constants.CODE_9001;
								rf.meta.message = Constants.ERROR_MESSAGE;
								return Response.status(Constants.CODE_9001).entity(rf).build();
							}					
						}
						else {
							rf.meta.id = Constants.CODE_2505;
							rf.meta.message = Constants.POST_NOT_BELONG_USER;
							return Response.status(Constants.CODE_2505).entity(rf).build();
						}			
					}
					else {
						rf.meta.id = Constants.CODE_1003;
						rf.meta.message =  Constants.TOKEN_INVALID;
						return Response.status(Constants.CODE_1003).entity(rf).build();
					}			
				}
				else {
					rf.meta.id = Constants.CODE_1002;
					rf.meta.message = Constants.TOKEN_EXPIRED;
					return Response.status(Constants.CODE_1002).entity(rf).build();
				}
			} 
			else {
				rf.meta.id = Constants.CODE_9002;
				rf.meta.message = Constants.TOKEN_MISSING;
				return Response.status(Constants.CODE_9002).entity(rf).build();
			}	
		}
		else {
			rf.meta.id = Constants.CODE_2504;
			rf.meta.message = Constants.POST_NOT_EXISTED;
			return Response.status(Constants.CODE_2504).entity(rf).build();
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
//						if (post.getUser_id() == user.getId()) {
						if (post.getUser().getId() == user.getId()) {
							post.setStatus(false);
							post.setUsername(user.getUsername());
							// Update post
							if (postsService.updatePost(post) == true) {
								rf.meta.id = Constants.CODE_200;
								rf.meta.message = Constants.POST_DEACTIVE_SUCCESS;
								rf.data = "Post id: " + post.getId() + "  " + "Status: " + post.isStatus();
								return Response.status(200).entity(rf).build();
							}
							else {
								rf.meta.id = Constants.CODE_9001;
								rf.meta.message = Constants.ERROR_MESSAGE;
								return Response.status(Constants.CODE_9001).entity(rf).build();
							}					
						}
						else {
							rf.meta.id = Constants.CODE_2505;
							rf.meta.message = Constants.POST_NOT_BELONG_USER;
							return Response.status(Constants.CODE_2505).entity(rf).build();
						}			
					}
					else {
						rf.meta.id = Constants.CODE_1003;
						rf.meta.message = Constants.TOKEN_INVALID;
						return Response.status(Constants.CODE_1003).entity(rf).build();
					}			
				}
				else {
					rf.meta.id = Constants.CODE_1002;
					rf.meta.message = Constants.TOKEN_EXPIRED;
					return Response.status(Constants.CODE_1002).entity(rf).build();
				}
			} 
			else {
				rf.meta.id = Constants.CODE_9002;
				rf.meta.message = Constants.TOKEN_MISSING;
				return Response.status(Constants.CODE_9002).entity(rf).build();
			}
		}
		else {
			rf.meta.id = Constants.CODE_2504;
			rf.meta.message = Constants.POST_NOT_EXISTED;
			return Response.status(Constants.CODE_2504).entity(rf).build();
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
			if (data.getTitle() != null && !data.getTitle().isEmpty() &&
				data.getDescription() != null && !data.getDescription().isEmpty() &&
				data.getContent() != null && !data.getContent().isEmpty()) {
				
				// Check token null
				if (access_token != null) {
					// Check whether token has expired or not
					if (tokensService.isCheckTokenValid(access_token) == true) {
						// Get user by access token
						Users user = usersService.getUserByToken(access_token);
						// Check token valid
						if (user != null) {
							// Check whether post belongs to right user
//							if (post.getUser_id() == user.getId()) {
							if (post.getUser().getId() == user.getId()) {
								// Set post object by data
								post.setTitle(data.getTitle());
								post.setDescription(data.getDescription());
								post.setContent(data.getContent());
								post.setImage(data.getImage());
								post.setModified_at(Calendar.getInstance().getTime());
								post.setUsername(user.getUsername());
								// Update post
								if (postsService.updatePost(post) == true) {
									rf.meta.id = Constants.CODE_200;
									rf.meta.message = Constants.POST_UPDATE_SUCCESS;
									rf.data = post;
									return Response.status(Constants.CODE_200).entity(rf).build();
								}
								else {
									rf.meta.id = Constants.CODE_9001;
									rf.meta.message = Constants.ERROR_MESSAGE;
									return Response.status(Constants.CODE_9001).entity(rf).build();
								}					
							}
							else {
								rf.meta.id = Constants.CODE_2505;
								rf.meta.message = Constants.POST_NOT_BELONG_USER;
								return Response.status(Constants.CODE_2505).entity(rf).build();
							}
						}
						else {
							rf.meta.id = Constants.CODE_1003;
							rf.meta.message = Constants.TOKEN_INVALID;
							return Response.status(Constants.CODE_1003).entity(rf).build();
						}
					}
					else {
						rf.meta.id = Constants.CODE_1002;
						rf.meta.message = Constants.TOKEN_EXPIRED;
						return Response.status(Constants.CODE_1002).entity(rf).build();
					}
				}
				else {
					rf.meta.id = Constants.CODE_9002;
					rf.meta.message = Constants.TOKEN_MISSING;
					return Response.status(Constants.CODE_9002).entity(rf).build();
				}
			}
			else {
				rf.meta.id = Constants.CODE_1001;
				rf.meta.message = Constants.INPUT_FAILED;
				return Response.status(Constants.CODE_1001).entity(rf).build();
			}
		}
		else {
			rf.meta.id = Constants.CODE_2504;
			rf.meta.message = Constants.POST_NOT_EXISTED;
			return Response.status(Constants.CODE_2504).entity(rf).build();
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
//						if (post.getUser_id() == user.getId()) {
						if (post.getUser().getId() == user.getId()) {
							if (postsService.deletePost(id) == true) {
								rf.meta.id = Constants.CODE_200;
								rf.meta.message = Constants.POST_DELETE_SUCCESS;
								return Response.status(Constants.CODE_200).entity(rf).build();
							}
							else {
								rf.meta.id = Constants.CODE_9001;
								rf.meta.message = Constants.ERROR_MESSAGE;
								return Response.status(Constants.CODE_9001).entity(rf).build();
							}
						}
						else {
							rf.meta.id = Constants.CODE_2505;
							rf.meta.message = Constants.POST_NOT_BELONG_USER;
							return Response.status(Constants.CODE_2505).entity(rf).build();
						}	
					}
					else {
						rf.meta.id = Constants.CODE_1003;
						rf.meta.message = Constants.TOKEN_INVALID;
						return Response.status(Constants.CODE_1003).entity(rf).build();
					}
				}
				else {
					rf.meta.id = Constants.CODE_1002;
					rf.meta.message = Constants.TOKEN_EXPIRED;
					return Response.status(Constants.CODE_1002).entity(rf).build();
				}							
			}
			else {
				rf.meta.id = Constants.CODE_9002;
				rf.meta.message = Constants.TOKEN_MISSING;
				return Response.status(Constants.CODE_9002).entity(rf).build();
			}
		}
		else {
			rf.meta.id = Constants.CODE_2504;
			rf.meta.message = Constants.POST_NOT_EXISTED;
			return Response.status(Constants.CODE_2504).entity(rf).build();
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
			rf.meta.id = Constants.CODE_200;
			rf.meta.message = Constants.POST_GETALL_SUCCESS;
			rf.data = listPost;
			return Response.status(Constants.CODE_200).entity(rf).build();
		}
		else {
			rf.meta.id = Constants.CODE_9001;
			rf.meta.message = Constants.ERROR_MESSAGE;
			return Response.status(Constants.CODE_9001).entity(rf).build();
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
					List<Posts> listPost = postsService.getPostsForUser(user);
					if (listPost != null) {
						rf.meta.id = Constants.CODE_200;
						rf.meta.message = Constants.POST_GET_FOR_USER_SUCCESS;
						rf.data = listPost;
						return Response.status(Constants.CODE_200).entity(rf).build();
					}
					else {
						rf.meta.id = Constants.CODE_9001;
						rf.meta.message = Constants.ERROR_MESSAGE;
						return Response.status(Constants.CODE_9001).entity(rf).build();
					}
				}
				else {
					rf.meta.id = Constants.CODE_1003;
					rf.meta.message = Constants.TOKEN_INVALID;
					return Response.status(Constants.CODE_1003).entity(rf).build();
				}
			}
			else {
				rf.meta.id = Constants.CODE_1002;
				rf.meta.message = Constants.TOKEN_EXPIRED;
				return Response.status(Constants.CODE_1002).entity(rf).build();
			}
		}
		else {
			rf.meta.id = Constants.CODE_9002;
			rf.meta.message = Constants.TOKEN_MISSING;
			return Response.status(Constants.CODE_9002).entity(rf).build();
		}	
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPostById(@HeaderParam("access_token") String access_token, @PathParam("id") int id) 
	{
		ResponseFormat rf = new ResponseFormat();
		
		// Get user by id
		Posts post = postsService.getPostById(id);		
		if (post != null) {
			rf.meta.id = Constants.CODE_200;
			rf.meta.message = Constants.POST_GET_POST_BY_ID_SUCCESS;
			rf.data = post;
			return Response.status(Constants.CODE_200).entity(rf).build();
		}
		else {
			rf.meta.id = Constants.CODE_2504;
			rf.meta.message = Constants.POST_NOT_EXISTED;
			return Response.status(Constants.CODE_2504).entity(rf).build();
		}		
		
	}
	
	@GET
	@Path("getlatestpost")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getLatestPost() 
	{
		ResponseFormat rf= new ResponseFormat();
		
		List<Posts> listPost = postsService.get10LatestPost();
		if (listPost != null) {
			rf.meta.id = Constants.CODE_200;
			rf.meta.message = Constants.POST_GET_LATEST_SUCCESS;
			rf.data = listPost;
			return Response.status(Constants.CODE_200).entity(rf).build();
		}
		else {
			rf.meta.id = Constants.CODE_9001;
			rf.meta.message = Constants.ERROR_MESSAGE;
			return Response.status(Constants.CODE_9001).entity(rf).build();
		}
	}
	
}
