package com.mulodo.miniblog.controller;

import java.util.Date;
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

import com.mulodo.miniblog.model.Comments;
import com.mulodo.miniblog.model.Posts;
import com.mulodo.miniblog.model.Users;
import com.mulodo.miniblog.responseformat.ResponseFormat;
import com.mulodo.miniblog.service.CommentsService;
import com.mulodo.miniblog.service.PostsService;
import com.mulodo.miniblog.service.TokensService;
import com.mulodo.miniblog.service.UsersService;

@Path("v1/posts")
@Controller
public class CommentsController 
{
	@Autowired
	CommentsService commentsService;
	@Autowired
	TokensService tokensService;
	@Autowired
	PostsService postsService;
	@Autowired
	UsersService usersService;
	
	
	@POST
	@Path("{post_id}/comments/add")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addComment(Comments data, @PathParam("post_id") int post_id, 
			@HeaderParam("access_token") String access_token) 
	{
		ResponseFormat rf = new ResponseFormat();
		
		// Check token null
		if (access_token != null) {
			// Check whether token expired 
			if (tokensService.isCheckTokenValid(access_token) == true) {
				// Check input
				if (data.getContent() != null & data.getContent().matches(".*\\w.*")) {
					Comments cmt = new Comments();
					cmt.setContent(data.getContent());
					cmt.setCreated_at(new Date());
					cmt.setModified_at(new Date());
					cmt.setPost_id(post_id);
					cmt.setUser_id(usersService.getUserByToken(access_token).getId());					
					// Check whether comment added success
					if (commentsService.isAddComment(cmt) == true) {			
						rf.meta.id = 200;
						rf.meta.message = "Comment created success";
						rf.data = cmt;
						return Response.status(200).entity(rf).build();
					}
					else {
						rf.meta.id = 3009;
						rf.meta.message = "Add comment failed.";
						return Response.status(3009).entity(rf).build();
					}
				}
				else {
					rf.meta.id = 1001;
					rf.meta.message = "Input invalid";
					return Response.status(1001).entity(rf).build();
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
		
	@PUT
	@Path("{post_id}/comments/update/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response edit(Comments data, @PathParam("post_id") int post_id, @PathParam("id") int id, 
			@HeaderParam("access_token") String access_token) 
	{
		ResponseFormat rf = new ResponseFormat();
		
		Posts post = new Posts();
		// Get post by post id
		post = postsService.getPostById(post_id);
		// Check post existed
		if (post != null) {
			// Check input
			if (data.getContent() != null && data.getContent().matches(".*\\w.*")) {
				// Check token null
				if (access_token != null) {
					// Check whether token has expired or not
					if (tokensService.isCheckTokenValid(access_token) == true) {
						// Get user by access token
						Users user = usersService.getUserByToken(access_token);
						// Check token valid
						if (user != null) {
							Comments cmt = new Comments();
							// Get comment by Id
							cmt = commentsService.getComment(id);
							// Check comment existed
							if (cmt != null) {
								// Check comment belongs to post
								if (cmt.getPost_id() == post.getId()) {
									// Check whether comment belongs to right user
									if (user.getId() == cmt.getUser_id()) {
										// Set edited data to comment object									
										cmt.setContent(data.getContent());
										cmt.setModified_at(new Date());
										// Edit comment
										if (commentsService.isUpdateComment(cmt) == true) {
											rf.meta.id = 200;
											rf.meta.message = "Comment edited success";
											rf.data = cmt;
											return Response.status(200).entity(rf).build();
										}
										else {
											rf.meta.id = 3008;
											rf.meta.message = "Edit comment failed.";
											return Response.status(3008).entity(rf).build();
										}
									}
									else {
										rf.meta.id = 3003;
										rf.meta.message = "Comment does not belongs to this user: " + user.getId();
										return Response.status(3003).entity(rf).build();
									}
								}
								else {
									rf.meta.id = 3004;
									rf.meta.message = "Comment does not belongs to this post: " + post.getId();
									return Response.status(3004).entity(rf).build();
								}
							}
							else {
								rf.meta.id = 3002;
								rf.meta.message = "Comment is not existed.";
								return Response.status(3002).entity(rf).build();
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
	@Path("{post_id}/comments/delete/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteComment(@PathParam("id") int id, @PathParam("post_id") int post_id, @HeaderParam("access_token") String access_token) 
	{
		ResponseFormat rf = new ResponseFormat();
		
		Posts post = new Posts();
		// Get post by post id
		post = postsService.getPostById(post_id);
		// Check post existed
		if (post != null) {
			// Check token null
			if (access_token != null) {
				// Check whether token has expired or not
				if (tokensService.isCheckTokenValid(access_token) == true) {
					// Get user by access token
					Users user = usersService.getUserByToken(access_token);
					// Check token valid
					if (user != null) {
						Comments cmt = new Comments();
						// Get comment by Id
						cmt = commentsService.getComment(id);
						// Check comment existed
						if (cmt != null) {
							// Check comment belongs to post
							if (cmt.getPost_id() == post.getId()) {
								// Check whether comment belongs to right user
								if (user.getId() == cmt.getUser_id()) {
									// Delete comment
									if (commentsService.isDeleteComment(cmt) == true) {
										rf.meta.id = 200;
										rf.meta.message = "Comment deleted success";
										return Response.status(200).entity(rf).build();
									}
									else {
										rf.meta.id = 3007;
										rf.meta.message = "Delete comment failed.";
										return Response.status(3007).entity(rf).build();
									}
								}
								else {
									rf.meta.id = 3003;
									rf.meta.message = "Comment does not belongs to this user: " + user.getId();
									return Response.status(3003).entity(rf).build();
								}
							}
							else {
								rf.meta.id = 3004;
								rf.meta.message = "Comment does not belongs to this post: " + post.getId();
								return Response.status(3004).entity(rf).build();
							}
						}
						else {
							rf.meta.id = 3002;
							rf.meta.message = "Comment is not existed.";
							return Response.status(3002).entity(rf).build();
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
			rf.meta.id = 2504;
			rf.meta.message = "Post is not existed.";
			return Response.status(2504).entity(rf).build();
		}
	}
	
	@GET	
	@Path("{post_id}/comments/getcommentsofpost")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllCommentsForPost(@PathParam("post_id") int post_id)
	{
		ResponseFormat rf = new ResponseFormat();
		Posts post = postsService.getPostById(post_id);
		if (post != null) {
			List<Comments> listComment = commentsService.getCommentsByPostId(post_id);
			// Check whether post have comment(s) or not
			if (listComment != null) {
				rf.meta.id = 200;
				rf.meta.message = "Get post's comments success ";
				rf.data = listComment;
				return Response.status(200).entity(rf).build();
			}
			else {
				rf.meta.id = 3005;
				rf.meta.message = "Get all comments for a post failed";
				return Response.status(3005).entity(rf).build();
			}			
		}
		else {
			rf.meta.id = 2504;
			rf.meta.message = "Post is not existed.";
			return Response.status(2504).entity(rf).build();
		}		
	}
	
	@GET	
	@Path("comments/getcommentsofuser")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllCommentsForUser(@HeaderParam("access_token") String access_token)
	{
		ResponseFormat rf = new ResponseFormat();
		// Check token null
		if (access_token != null) {
			// Check whether token has expired or not
			if (tokensService.isCheckTokenValid(access_token) == true) {
				// Get user by access token
				Users user = usersService.getUserByToken(access_token);
				// Check token valid
				if (user != null) {
					List<Comments> listComment = commentsService.getCommentsByUserId(user.getId());
					// Check whether user have written comment(s) or not
					if (listComment != null) {
						rf.meta.id = 200;
						rf.meta.message = "Get user's comments success";
						rf.data = listComment;
						return Response.status(200).entity(rf).build();
					}
					else {
						rf.meta.id = 3006;
						rf.meta.message = "Get all comments for user failed.";
						return Response.status(3006).entity(rf).build();
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
}
