package com.mulodo.miniblog.controller;

import java.util.Calendar;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.mulodo.miniblog.model.Posts;
import com.mulodo.miniblog.model.Users;
import com.mulodo.miniblog.responseformat.Meta;
import com.mulodo.miniblog.responseformat.Response;
import com.mulodo.miniblog.service.PostsService;
import com.mulodo.miniblog.service.UsersService;

@Path("v1/posts")
@Controller
public class PostsController {

	@Autowired
	PostsService postsService;
	@Autowired
	UsersService usersService;
	
	@POST
	@Path("create")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response CreatePost(Posts data, @HeaderParam("access_token") String access_token) {
		Meta meta = new Meta();
		Response res = new Response();
		
		if (data.getTitle() != null && !data.getTitle().isEmpty() && data.getDescription() != null && 
				!data.getDescription().isEmpty() && data.getContent() != null && !data.getContent().isEmpty()) {
						
			Posts post = new Posts();
			post.setTitle(data.getTitle());
			post.setDescription(data.getDescription());
			post.setContent(data.getContent());
			post.setImage(data.getImage());
			post.setStatus(true);
			post.setCreated_at(Calendar.getInstance().getTime());
			post.setModified_at(Calendar.getInstance().getTime());
			
			if (access_token != null)
				post.setUser_id(usersService.GetUserByToken(access_token).getId());	
			else {
				meta.setId(1002);
				meta.setMessage("Access token has expired.");
				res.setMeta(meta);
			}
							
			if (postsService.createPost(post) == true) {			
				meta.setId(205);
				meta.setMessage("New post is created success");
				res.setMeta(meta);
				res.setData(post);
			}
			else {
				meta.setId(9001);
				meta.setMessage("Error.");
				res.setMeta(meta);
			}					
		}
		else {
			meta.setId(9001);
			meta.setMessage("Input invalid");
			res.setMeta(meta);		
		}		
		return res;
	}
		
	@PUT
	@Path("{id}/{status}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response UpdatePostStatus(@PathParam("id") int id, @PathParam("status") boolean status, @HeaderParam("access_token") String access_token) {
		Meta meta = new Meta();
		Response res = new Response();
		
		// Get post by post id
		Posts post = new Posts();
		post = postsService.GetPostById(id);
		
		// Check whether token has expired or not
		if (access_token != null) {
			// Get user by access token
			Users user = usersService.GetUserByToken(access_token);
			// Check whether post belongs to right user
			if (post.getUser_id() == user.getId()) {
				post.setStatus(status);
				// Update post
				if (postsService.UpdatePost(post) == true) {
					meta.setId(207);
					meta.setMessage("Post's status updated success");
					res.setMeta(meta);
					res.setData("Post id: " + post.getId() + "  " + "Status: " + post.isStatus());
				}
				else {
					meta.setId(9001);
					meta.setMessage("Error.");
					res.setMeta(meta);
				}					
			}
			else {
				meta.setId(2505);
				meta.setMessage("This post does not belong to the user with id: " + user.getId());
				res.setMeta(meta);
			}			
		}
		else {
			meta.setId(1002);
			meta.setMessage("Access token has expired.");
			res.setMeta(meta);	
		}		
		return res;
	}
	
	@PUT
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response EditPost(@PathParam("id") int id, Posts data, @HeaderParam("access_token") String access_token) {
		Response res = new Response();
		Meta meta = new Meta();
		
		// Get post by post id
		Posts post = new Posts();
		post = postsService.GetPostById(id);
		
		if(data.getTitle() != null && data.getTitle().matches(".*\\w.*") &&
			data.getDescription() != null && data.getDescription().matches(".*\\w.*") &&
			data.getContent() != null && data.getContent().matches(".*\\w.*")) {
			
			// Check whether token has expired or not
			if (access_token != null) {
				// Get user by access token
				Users user = usersService.GetUserByToken(access_token);
				// Check whether post belongs to right user
				if (post.getUser_id() == user.getId()) {
					// Set post object by data
					post.setTitle(data.getTitle());
					post.setDescription(data.getDescription());
					post.setContent(data.getContent());
					post.setImage(data.getImage());
					post.setModified_at(Calendar.getInstance().getTime());
					// Update post
					if (postsService.UpdatePost(post) == true) {
						meta.setId(208);
						meta.setMessage("Post updated success");
						res.setMeta(meta);
						res.setData(post);
					}
					else {
						meta.setId(9001);
						meta.setMessage("Error.");
						res.setMeta(meta);
					}					
				}
				else {
					meta.setId(2505);
					meta.setMessage("This post does not belong to the user with id: " + user.getId());
					res.setMeta(meta);
				}			
			}
			else {
				meta.setId(1002);
				meta.setMessage("Access token has expired.");
				res.setMeta(meta);	
			}					
		}
		else {
			meta.setId(1001);
			meta.setMessage("Input failed.");
			res.setMeta(meta);	
		}		
		return res;
	}
	
	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response DeletePost(@PathParam("id") int id, @HeaderParam("access_token") String access_token) {
		Response res = new Response();
		Meta meta = new Meta();
		
		Posts post = postsService.GetPostById(id);
		
		if (access_token != null) {
			// Get user by access token
			Users user = usersService.GetUserByToken(access_token);
			// Check whether post belongs to right user
			if (post.getUser_id() == user.getId()) {
				if (postsService.DeletePost(id) == true) {
					meta.setId(208);
					meta.setMessage("Post deleted success");
					res.setMeta(meta);
				}
				else {
					meta.setId(9001);
					meta.setMessage("Error");
					res.setMeta(meta);
				}
			}
			else {
				meta.setId(2505);
				meta.setMessage("This post does not belong to the user with id: " + user.getId());
				res.setMeta(meta);
			}
			
		}
		else {
			meta.setId(1002);
			meta.setMessage("Access token has expired.");
			res.setMeta(meta);
		}		
		return res;
	}
	
}
