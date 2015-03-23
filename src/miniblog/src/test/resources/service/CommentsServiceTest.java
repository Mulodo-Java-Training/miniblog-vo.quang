package service;

import static org.junit.Assert.assertEquals;
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

import com.mulodo.miniblog.model.Comments;
import com.mulodo.miniblog.model.Posts;
import com.mulodo.miniblog.model.Users;
import com.mulodo.miniblog.resteasy.form.LoginForm;
import com.mulodo.miniblog.service.CommentsService;
import com.mulodo.miniblog.service.PostsService;
import com.mulodo.miniblog.service.TokensService;
import com.mulodo.miniblog.service.UsersService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/applicationContext.xml")
public class CommentsServiceTest 
{
	@Autowired
	UsersService usersService;
	@Autowired
	PostsService postsService;
	@Autowired
	CommentsService commentsService;
	@Autowired
	TokensService tokensService;
	
	static Users userLogin;
	Posts post = new Posts();
	Comments cmt = new Comments();
	
	@Before
	public void setup() 
	{
		// Login
		LoginForm form = new LoginForm();
		form.setUsername("username");
		form.setPassword("password");
		usersService.isLogin(form.getUsername(), form.getPassword());		
		userLogin = usersService.getUserByUsername(form.getUsername());
		tokensService.isCreateToken(userLogin);
		
		// Create new post
		post.setTitle("TestPost");
		post.setDescription("TestPost");
		post.setContent("TestPost");
		post.setCreated_at(new Date());
		post.setModified_at(new Date());
		Users user = new Users();
		user.setId(userLogin.getId());
		post.setUser(user);
		postsService.createPost(post);
		
	}
	
	@After
	public void tearDown()
	{
		// Delete comment
		assertTrue(commentsService.isDeleteComment(cmt));
		// Delete post
		assertTrue(postsService.deletePost(post.getId()));
		// Logout
		tokensService.isDeleteTokenByUserId(userLogin.getId());
	}
	
	@Test
	public void commentsTest()
	{	
		// Add comment
		cmt.setContent("TestAddComment");
		cmt.setCreated_at(new Date());
		cmt.setModified_at(new Date());
		Posts post1 = postsService.getPostById(post.getId());
		cmt.setPost(post1);
		Users user1 = new Users();
		user1.setId(userLogin.getId());
		cmt.setUser(user1);
		
		assertTrue(commentsService.isAddComment(cmt));
		Comments actualCmt = commentsService.getComment(cmt.getId());
		assertEquals("TestAddComment", actualCmt.getContent());
		assertEquals(cmt.getPost().getId(), actualCmt.getPost().getId());
		assertEquals(cmt.getUser().getId(), actualCmt.getUser().getId());
		
		// Edit comment
		actualCmt.setContent("Edit Comment");
		actualCmt.setModified_at(new Date());
		assertTrue(commentsService.isUpdateComment(actualCmt));
		Comments actualEditedCmt = commentsService.getComment(cmt.getId());
		assertEquals("Edit Comment", actualEditedCmt.getContent());
		
		// Get all comment for a post
		List<Comments> listCmt = commentsService.getCommentsByPostId(post);
		assertTrue(listCmt.size() > 0);
		
		// Get all comment for user
		List<Comments> listCmtUser = commentsService.getCommentsByUserId(userLogin.getId());
		assertTrue(listCmtUser.size() > 0);		
	}
}
