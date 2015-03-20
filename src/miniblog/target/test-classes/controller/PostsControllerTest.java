package controller;

import static org.junit.Assert.*;

import javax.ws.rs.core.Response;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mulodo.miniblog.controller.PostsController;
import com.mulodo.miniblog.model.Posts;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/test/resources/TestControllerContext.xml")
public class PostsControllerTest 
{
	@Autowired
	private PostsController postsController;
	
	/**
	 * Test createPost success
	 * 200
	 * 
	 * */	
	@Test
	public void createPostTestSuccess() 
	{	
		String access_token = "abc123xyz";
		Posts data = new Posts();
		data.setTitle("Title");
		data.setDescription("Description");
		data.setContent("Content");
		
		Response resp = postsController.createPost(data, access_token);
		assertEquals(200, resp.getStatus());		
	}

	/**
	 * Test createPost failed 1
	 * 1001
	 * Input Invalid
	 * title empty
	 * 
	 * */	
	@Test
	public void createPostTestFailed1() 
	{	
		String access_token = "abc123xyz";
		Posts data = new Posts();
		data.setTitle("");
		data.setDescription("Description");
		data.setContent("Content");
		
		Response resp = postsController.createPost(data, access_token);
		assertEquals(1001, resp.getStatus());		
	}
	
	/**
	 * Test createPost failed 2
	 * 1001
	 * Input Invalid
	 * description empty
	 * 
	 * */	
	@Test
	public void createPostTestFailed2() 
	{	
		String access_token = "abc123xyz";
		Posts data = new Posts();
		data.setTitle("Title");
		data.setDescription("");
		data.setContent("Content");
		
		Response resp = postsController.createPost(data, access_token);
		assertEquals(1001, resp.getStatus());		
	}
	
	/**
	 * Test createPost failed 3
	 * 1001
	 * Input Invalid
	 * content empty
	 * 
	 * */	
	@Test
	public void createPostTestFailed3() 
	{	
		String access_token = "abc123xyz";
		Posts data = new Posts();
		data.setTitle("Title");
		data.setDescription("Description");
		data.setContent("");
		
		Response resp = postsController.createPost(data, access_token);
		assertEquals(1001, resp.getStatus());		
	}
	
	/**
	 * Test createPost failed 4
	 * 9002
	 * access_token null
	 * 
	 * */	
	@Test
	public void createPostTestFailed4() 
	{	
		String access_token = null;
		Posts data = new Posts();
		data.setTitle("Title");
		data.setDescription("Description");
		data.setContent("Content");
		
		Response resp = postsController.createPost(data, access_token);
		assertEquals(9002, resp.getStatus());		
	}
	
	/**
	 * Test createPost failed 5
	 * 1002
	 * token expired
	 * 
	 * */	
	@Test
	public void createPostTestFailed5() 
	{	
		String access_token = "expired";
		Posts data = new Posts();
		data.setTitle("Title");
		data.setDescription("Description");
		data.setContent("Content");
		
		Response resp = postsController.createPost(data, access_token);
		assertEquals(1002, resp.getStatus());		
	}
	
	/**
	 * Test createPost failed 6
	 * 9001
	 * token expired
	 * 
	 * */	
	@Test
	public void createPostTestFailed6() 
	{	
		String access_token = "abc123xyz";
		Posts data = new Posts();
		data.setTitle("TitleFailed");
		data.setDescription("Description");
		data.setContent("Content");
		
		Response resp = postsController.createPost(data, access_token);
		assertEquals(9001, resp.getStatus());		
	}
	
	/**
	 * Test activePostStatus success 
	 * 200
	 * 
	 * */	
	@Test
	public void activePostStatusSuccess() 
	{	
		String access_token = "abc123xyz";
		
		Response resp = postsController.activePostStatus(1, access_token);
		assertEquals(200, resp.getStatus());		
	}
	
	/**
	 * Test activePostStatus failed 1
	 * 2504
	 * Post is not existed
	 * 
	 * */	
	@Test
	public void activePostStatusFailed1() 
	{	
		String access_token = "abc123xyz";

		Response resp = postsController.activePostStatus(2, access_token);
		assertEquals(2504, resp.getStatus());		
	}
	
	/**
	 * Test activePostStatus failed 2
	 * 9002
	 * Missing token
	 * 
	 * */	
	@Test
	public void activePostStatusFailed2() 
	{	
		String access_token = null;

		Response resp = postsController.activePostStatus(1, access_token);
		assertEquals(9002, resp.getStatus());		
	}
	
	/**
	 * Test activePostStatus failed 3
	 * 1002
	 * Token expired
	 * 
	 * */	
	@Test
	public void activePostStatusFailed3() 
	{	
		String access_token = "expired";

		Response resp = postsController.activePostStatus(1, access_token);
		assertEquals(1002, resp.getStatus());		
	}
	
	/**
	 * Test activePostStatus failed 4
	 * 1003
	 * Token invalid
	 * 
	 * */	
	@Test
	public void activePostStatusFailed4() 
	{	
		String access_token = "token_invalid";

		Response resp = postsController.activePostStatus(1, access_token);
		assertEquals(1003, resp.getStatus());		
	}
	
	/**
	 * Test activePostStatus failed 5
	 * 2505
	 * This post does not belong to the user
	 * 
	 * */	
	@Test
	public void activePostStatusFailed5() 
	{	
		String access_token = "missed_match_token";

		Response resp = postsController.activePostStatus(1, access_token);
		assertEquals(2505, resp.getStatus());		
	}
	
	/**
	 * Test activePostStatus failed 6
	 * 9001
	 * This post does not belong to the user
	 * 
	 * */	
	@Test
	public void activePostStatusFailed6() 
	{	
		String access_token = "access_token";

		Response resp = postsController.activePostStatus(3, access_token);
		assertEquals(9001, resp.getStatus());		
	}
	
	/**
	 * Test deactivePostStatus success 
	 * 200
	 * 
	 * */	
	@Test
	public void deactivePostStatusSuccess() 
	{	
		String access_token = "abc123xyz";
		
		Response resp = postsController.deactivePostStatus(1, access_token);
		assertEquals(200, resp.getStatus());		
	}
	
	/**
	 * Test deactivePostStatus failed 
	 * Same with activePostStatus failed
	 * 
	 * */
	
	/**
	 * Test editPostStatus success 
	 * 200
	 * 
	 * */	
	@Test
	public void editPostStatusSuccess() 
	{	
		String access_token = "abc123xyz";
		Posts data = new Posts();
		data.setTitle("UpdatedTitle");
		data.setDescription("UpdatedDescription");
		data.setContent("UpdatedContent");
		
		Response resp = postsController.editPost(1, data, access_token);
		assertEquals(200, resp.getStatus());		
	}
	
	/**
	 * Test editPostStatus failed 1
	 * 2504
	 * Post is not existed
	 * 
	 * */	
	@Test
	public void editPostStatusFailed1() 
	{	
		String access_token = "access_token";
		Posts data = new Posts();
		data.setTitle("UpdatedTitle");
		data.setDescription("UpdatedDescription");
		data.setContent("UpdatedContent");

		Response resp = postsController.editPost(100, data, access_token);
		assertEquals(2504, resp.getStatus());		
	}
	
	/**
	 * Test editPostStatus failed 2
	 * 1001
	 * Input post failed
	 * title empty
	 * 
	 * */	
	@Test
	public void editPostStatusFailed2() 
	{	
		String access_token = "access_token";
		Posts data = new Posts();
		data.setTitle("");
		data.setDescription("UpdatedDescription");
		data.setContent("UpdatedContent");

		Response resp = postsController.editPost(1, data, access_token);
		assertEquals(1001, resp.getStatus());		
	}
	
	/**
	 * Test editPostStatus failed 3
	 * 1001
	 * Input post failed
	 * description empty
	 * 
	 * */	
	@Test
	public void editPostStatusFailed3() 
	{	
		String access_token = "access_token";
		Posts data = new Posts();
		data.setTitle("UpdatedTitle");
		data.setDescription("");
		data.setContent("UpdatedContent");

		Response resp = postsController.editPost(1, data, access_token);
		assertEquals(1001, resp.getStatus());		
	}
	
	/**
	 * Test editPostStatus failed 4
	 * 1001
	 * Input post failed
	 * content empty
	 * 
	 * */	
	@Test
	public void editPostStatusFailed4() 
	{	
		String access_token = "access_token";
		Posts data = new Posts();
		data.setTitle("UpdatedTitle");
		data.setDescription("UpdatedDescription");
		data.setContent("");

		Response resp = postsController.editPost(1, data, access_token);
		assertEquals(1001, resp.getStatus());		
	}
	
	/**
	 * Test editPostStatus failed 5
	 * 9002
	 * access_token null
	 * Missing access_token
	 * 
	 * */	
	@Test
	public void editPostStatusFailed5() 
	{	
		String access_token = null;
		Posts data = new Posts();
		data.setTitle("UpdatedTitle");
		data.setDescription("UpdatedDescription");
		data.setContent("UpdatedContent");

		Response resp = postsController.editPost(1, data, access_token);
		assertEquals(9002, resp.getStatus());		
	}
	
	/**
	 * Test editPostStatus failed 6
	 * 1002
	 * token expired
	 * 
	 * */	
	@Test
	public void editPostStatusFailed6() 
	{	
		String access_token = "token_expired";
		Posts data = new Posts();
		data.setTitle("UpdatedTitle");
		data.setDescription("UpdatedDescription");
		data.setContent("UpdatedContent");

		Response resp = postsController.editPost(1, data, access_token);
		assertEquals(1002, resp.getStatus());		
	}
	
	/**
	 * Test editPostStatus failed 7
	 * 1003
	 * token invalid
	 * 
	 * */	
	@Test
	public void editPostStatusFailed7() 
	{	
		String access_token = "token_invalid";
		Posts data = new Posts();
		data.setTitle("UpdatedTitle");
		data.setDescription("UpdatedDescription");
		data.setContent("UpdatedContent");

		Response resp = postsController.editPost(1, data, access_token);
		assertEquals(1003, resp.getStatus());		
	}
	
	/**
	 * Test editPostStatus failed 8
	 * 2505
	 * This post does not belong to this user
	 * 
	 * */	
	@Test
	public void editPostStatusFailed8() 
	{	
		String access_token = "missed_match_token";
		Posts data = new Posts();
		data.setTitle("UpdatedTitle");
		data.setDescription("UpdatedDescription");
		data.setContent("UpdatedContent");

		Response resp = postsController.editPost(1, data, access_token);
		assertEquals(2505, resp.getStatus());		
	}
	
	/**
	 * Test editPostStatus failed 9
	 * 9001
	 * error
	 * 
	 * */	
	@Test
	public void editPostStatusFailed9() 
	{	
		String access_token = "access_token";
		Posts data = new Posts();
		data.setTitle("UpdatedTitle");
		data.setDescription("UpdatedDescription");
		data.setContent("UpdatedContent");

		Response resp = postsController.editPost(3, data, access_token);
		assertEquals(9001, resp.getStatus());		
	}
	
	/**
	 * Test deletePost success 
	 * 200
	 * 
	 * */	
	@Test
	public void deletePost() 
	{	
		String access_token = "abc123xyz";
		
		Response resp = postsController.deletePost(1, access_token);
		assertEquals(200, resp.getStatus());		
	}
	
	/**
	 * Test deletePost failed 1 
	 * 2504
	 * Post is not existed
	 * 
	 * */	
	@Test
	public void deletePostFailed1() 
	{	
		String access_token = "abc123xyz";
		
		Response resp = postsController.deletePost(100, access_token);
		assertEquals(2504, resp.getStatus());		
	}
	
	/**
	 * Test deletePost failed 2
	 * 9002
	 * Missing token
	 * 
	 * */	
	@Test
	public void deletePostFailed2() 
	{	
		String access_token = null;
		
		Response resp = postsController.deletePost(1, access_token);
		assertEquals(9002, resp.getStatus());		
	}
	
	/**
	 * Test deletePost failed 3
	 * 1002
	 * token expired
	 * 
	 * */	
	@Test
	public void deletePostFailed3() 
	{	
		String access_token = "token_expired";
		
		Response resp = postsController.deletePost(1, access_token);
		assertEquals(1002, resp.getStatus());		
	}
	
	/**
	 * Test deletePost failed 4
	 * 1003
	 * token invalid
	 * 
	 * */	
	@Test
	public void deletePostFailed4() 
	{	
		String access_token = "token_invalid";
		
		Response resp = postsController.deletePost(1, access_token);
		assertEquals(1003, resp.getStatus());		
	}
	
	/**
	 * Test deletePost failed 5
	 * 2505
	 * This post does not belong to this user
	 * 
	 * */	
	@Test
	public void deletePostFailed5() 
	{	
		String access_token = "access_token";
		
		Response resp = postsController.deletePost(1, access_token);
		assertEquals(2505, resp.getStatus());		
	}
	
	/**
	 * Test deletePost failed 6
	 * 9001
	 * Error
	 * 
	 * */	
	@Test
	public void deletePostFailed6() 
	{	
		String access_token = "access_token";
		
		Response resp = postsController.deletePost(3, access_token);
		assertEquals(9001, resp.getStatus());		
	}
	
	/**
	 * Test getAllPost success
	 * 200
	 * 
	 * */	
	@Test
	public void getAllPostSuccess() 
	{			
		Response resp = postsController.getAllPosts();
		assertEquals(200, resp.getStatus());		
	}
	
	/**
	 * Test getPostForUser success
	 * 200
	 * 
	 * */	
	@Test
	public void getPostForUserSuccess() 
	{			
		String access_token = "abc123xyz";
		int user_id = 1;
		Response resp = postsController.getPostforUser(access_token, user_id);
		assertEquals(200, resp.getStatus());		
	}
	
	/**
	 * Test getPostForUser failed 1
	 * 9002
	 * token null
	 * 
	 * */	
	@Test
	public void getPostForUserFailed1() 
	{			
		String access_token = null;
		int user_id = 1;
		Response resp = postsController.getPostforUser(access_token, user_id);
		assertEquals(9002, resp.getStatus());		
	}
	
	/**
	 * Test getPostForUser failed 2
	 * 1002
	 * token expired
	 * 
	 * */	
	@Test
	public void getPostForUserFailed2() 
	{			
		String access_token = "token_expired";
		int user_id = 1;
		Response resp = postsController.getPostforUser(access_token, user_id);
		assertEquals(1002, resp.getStatus());		
	}
	
	/**
	 * Test getPostForUser failed 3
	 * 1003
	 * token invalid
	 * 
	 * */	
	@Test
	public void getPostForUserFailed3() 
	{			
		String access_token = "token_invalid";
		int user_id = 1;
		Response resp = postsController.getPostforUser(access_token, user_id);
		assertEquals(1003, resp.getStatus());		
	}
	
	/**
	 * Test getPostForUser failed 4
	 * 9001
	 * Error
	 * 
	 * */	
	@Test
	public void getPostForUserFailed4() 
	{			
		String access_token = "access_token";
		int user_id = 1;
		Response resp = postsController.getPostforUser(access_token, user_id);
		assertEquals(9001, resp.getStatus());		
	}
	
}
