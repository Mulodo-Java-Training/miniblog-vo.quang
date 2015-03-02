package service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mulodo.miniblog.model.Posts;
import com.mulodo.miniblog.model.Users;
import com.mulodo.miniblog.resteasy.form.LoginForm;
import com.mulodo.miniblog.service.PostsService;
import com.mulodo.miniblog.service.TokensService;
import com.mulodo.miniblog.service.UsersService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/applicationContext.xml")
public class PostsServiceTest 
{
	@Autowired
	UsersService usersService;
	@Autowired
	PostsService postsService;
	@Autowired
	TokensService tokensService;
	
	static Users userLogin;
	
	@Before
	public void login() 
	{
		LoginForm form = new LoginForm();
		form.setUsername("username");
		form.setPassword("password");
		usersService.isLogin(form.getUsername(), form.getPassword());		
		userLogin = usersService.getUserByUsername(form.getUsername());
		tokensService.isCreateToken(userLogin);
	}
	
	@After
	public void logout()
	{
		tokensService.isDeleteTokenByUserId(userLogin.getId());
	}
	
	@Test
	public void createPost()
	{
		// Create new post
		Posts post = new Posts();
		post.setTitle("TestCreatePost");
		post.setDescription("TestCreatePost");
		post.setContent("TestCreatePost");
		post.setCreated_at(new Date());
		post.setModified_at(new Date());
		post.setUser_id(userLogin.getId());
				
		assertTrue(postsService.createPost(post));
		Posts actual = postsService.getPostById(post.getId());
		assertEquals("TestCreatePost", actual.getTitle());
		assertEquals("TestCreatePost", actual.getDescription());
		assertEquals("TestCreatePost", actual.getContent());
		
		
		// Deactive post status
		post.setStatus(false);
		postsService.updatePost(post);
		Posts deactiveStatus = postsService.getPostById(post.getId());
		assertFalse(deactiveStatus.isStatus());
		
		
		// Active post status
		post.setStatus(true);
		postsService.updatePost(post);
		Posts activeStatus = postsService.getPostById(post.getId());
		assertTrue(activeStatus.isStatus());
		
		
		// Edit post
		post.setTitle("TestEditPost");
		post.setDescription("TestEditPost");
		post.setContent("TestEditPost");
		post.setImage("TestEditPost");
		
		postsService.updatePost(post);
		Posts actualUpdatedPost = postsService.getPostById(post.getId());
		assertEquals("TestEditPost", actualUpdatedPost.getTitle());
		assertEquals("TestEditPost", actualUpdatedPost.getDescription());
		assertEquals("TestEditPost", actualUpdatedPost.getContent());
		assertEquals("TestEditPost", actualUpdatedPost.getImage());
		
		
		// Delete post
		assertTrue(postsService.deletePost(post.getId()));
		
		
		// Get all posts
		List<Posts> listPost = postsService.getAllPosts();
		assertTrue(listPost.size() > 0);
		
		
		// Get all post for user
		List<Posts> listPostUser = postsService.getPostsForUser(post.getUser_id());
		assertTrue(listPostUser.size() > 0);
		// Check posts belong to one user
		for (Posts p : listPostUser) {
			assertEquals(p.getUser_id(), post.getUser_id());
		}					
	}
}
