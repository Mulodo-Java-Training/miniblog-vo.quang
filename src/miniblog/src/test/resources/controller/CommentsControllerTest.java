package controller;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Response;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mulodo.miniblog.controller.CommentsController;
import com.mulodo.miniblog.model.Comments;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/test/resources/TestControllerContext.xml")
public class CommentsControllerTest 
{
	@Autowired
	private CommentsController commentsController;
	
	/**
	 * Test addComment success
	 * 200
	 * 
	 * */	
	@Test
	public void addCommentTestSuccess() 
	{	
		String access_token = "abc123xyz";
		Comments data = new Comments();
		data.setContent("TestComment");
		int post_id = 1;
		
		Response resp = commentsController.addComment(data, post_id, access_token);
		assertEquals(200, resp.getStatus());		
	}

	/**
	 * Test addComment failed 1
	 * 9002
	 * token null
	 * Missing token
	 * 
	 * */	
	@Test
	public void addCommentTestFailed1() 
	{	
		String access_token = null;
		Comments data = new Comments();
		data.setContent("TestComment");
		int post_id = 1;
		
		Response resp = commentsController.addComment(data, post_id, access_token);
		assertEquals(9002, resp.getStatus());		
	}

	/**
	 * Test addComment failed 2
	 * 1002
	 * token expired
	 * 
	 * */	
	@Test
	public void addCommentTestFailed2() 
	{	
		String access_token = "token_expired";
		Comments data = new Comments();
		data.setContent("TestComment");
		int post_id = 1;
		
		Response resp = commentsController.addComment(data, post_id, access_token);
		assertEquals(1002, resp.getStatus());		
	}
	
	/**
	 * Test addComment failed 3
	 * 1001
	 * content empty
	 * Input invalid
	 * 
	 * */	
	@Test
	public void addCommentTestFailed3() 
	{	
		String access_token = "abc123xyz";
		Comments data = new Comments();
		data.setContent("");
		int post_id = 1;
		
		Response resp = commentsController.addComment(data, post_id, access_token);
		assertEquals(1001, resp.getStatus());		
	}
	
	/**
	 * Test addComment failed 4
	 * 3009
	 * Add comment failed
	 * 
	 * */	
	@Test
	public void addCommentTestFailed4() 
	{	
		String access_token = "abc123xyz";
		Comments data = new Comments();
		data.setContent("TestComment");
		int post_id = 2;
		
		Response resp = commentsController.addComment(data, post_id, access_token);
		assertEquals(3009, resp.getStatus());		
	}
	
	/**
	 * Test edit success
	 * 200
	 * 
	 * */	
	@Test
	public void editTestSuccess() 
	{	
		String access_token = "abc123xyz";
		Comments data = new Comments();
		int id = 1;
		data.setContent("TestComment");
		int post_id = 1;
		
		Response resp = commentsController.edit(data, post_id, id, access_token);
		assertEquals(200, resp.getStatus());		
	}
	
	/**
	 * Test edit failed 1
	 * 2504
	 * Post is not existed
	 * 
	 * */	
	@Test
	public void editTestFailed1() 
	{	
		String access_token = "abc123xyz";
		Comments data = new Comments();
		int id = 1;
		data.setContent("TestComment");
		int post_id = 100;
		
		Response resp = commentsController.edit(data, post_id, id, access_token);
		assertEquals(2504, resp.getStatus());		
	}
	
	/**
	 * Test edit failed 2
	 * 1001
	 * content empty
	 * Input failed
	 * 
	 * */	
	@Test
	public void editTestFailed2() 
	{	
		String access_token = "abc123xyz";
		Comments data = new Comments();
		int id = 1;
		data.setContent("");
		int post_id = 1;
		
		Response resp = commentsController.edit(data, post_id, id, access_token);
		assertEquals(1001, resp.getStatus());		
	}
	
	/**
	 * Test edit failed 3
	 * 9002
	 * token null
	 * Missing token
	 * 
	 * */	
	@Test
	public void editTestFailed3() 
	{	
		String access_token = null;
		Comments data = new Comments();
		int id = 1;
		data.setContent("Content");
		int post_id = 1;
		
		Response resp = commentsController.edit(data, post_id, id, access_token);
		assertEquals(9002, resp.getStatus());		
	}
	
	/**
	 * Test edit failed 4
	 * 1002
	 * access_token expired
	 * 
	 * */	
	@Test
	public void editTestFailed4() 
	{	
		String access_token = "token_expired";
		Comments data = new Comments();
		int id = 1;
		data.setContent("Content");
		int post_id = 1;
		
		Response resp = commentsController.edit(data, post_id, id, access_token);
		assertEquals(1002, resp.getStatus());		
	}
	
	/**
	 * Test edit failed 5
	 * 1003
	 * token invalid
	 * 
	 * 
	 * */	
	@Test
	public void editTestFailed5() 
	{	
		String access_token = "token_invalid";
		Comments data = new Comments();
		int id = 1;
		data.setContent("Content");
		int post_id = 1;
		
		Response resp = commentsController.edit(data, post_id, id, access_token);
		assertEquals(1003, resp.getStatus());		
	}
	
	/**
	 * Test edit failed 6
	 * 3002
	 * Comment is not existed
	 * 
	 * 
	 * */	
	@Test
	public void editTestFailed6() 
	{	
		String access_token = "abc123xyz";
		Comments data = new Comments();
		int id = 100;
		data.setContent("Content");
		int post_id = 1;
		
		Response resp = commentsController.edit(data, post_id, id, access_token);
		assertEquals(3002, resp.getStatus());		
	}
	
	/**
	 * Test edit failed 7
	 * 3004
	 * Comment does not belong to this post
	 * 
	 * 
	 * */	
	@Test
	public void editTestFailed7() 
	{	
		String access_token = "abc123xyz";
		Comments data = new Comments();
		int id = 1;
		data.setContent("Content");
		int post_id = 3;
		
		Response resp = commentsController.edit(data, post_id, id, access_token);
		assertEquals(3004, resp.getStatus());		
	}
	
	/**
	 * Test edit failed 8
	 * 3003
	 * Comment does not belong to this user
	 * 
	 * 
	 * */	
	@Test
	public void editTestFailed8() 
	{	
		String access_token = "missed_match_token";
		Comments data = new Comments();
		int id = 1;
		data.setContent("Content");
		int post_id = 1;
		
		Response resp = commentsController.edit(data, post_id, id, access_token);
		assertEquals(3003, resp.getStatus());		
	}
	
	/**
	 * Test edit failed 9
	 * 3008
	 * Edit comment failed
	 * 
	 * 
	 * */	
	@Test
	public void editTestFailed9() 
	{	
		String access_token = "access_token";
		Comments data = new Comments();
		int id = 3;
		data.setContent("Content");
		int post_id = 3;
		
		Response resp = commentsController.edit(data, post_id, id, access_token);
		assertEquals(3008, resp.getStatus());		
	}
	
	/**
	 * Test delete success
	 * 200
	 * 
	 * */	
	@Test
	public void deleteTestSuccess() 
	{	
		String access_token = "token4";
		int id = 4;
		int post_id = 4;
		
		Response resp = commentsController.deleteComment(id, post_id, access_token);
		assertEquals(200, resp.getStatus());		
	}
	
	/**
	 * Test delete failed 1
	 * 2504
	 * Post is not existed
	 * 
	 * 
	 * */	
	@Test
	public void deleteTestFailed1() 
	{	
		String access_token = "abc123xyz";
		int id = 1;
		int post_id = 100;
		
		Response resp = commentsController.deleteComment(id, post_id, access_token);
		assertEquals(2504, resp.getStatus());		
	}
	
	/**
	 * Test delete failed 2
	 * 9002
	 * access_token null
	 * Missing token
	 * 
	 * */	
	@Test
	public void deleteTestFailed2() 
	{	
		String access_token = null;
		int id = 1;
		int post_id = 1;
		
		Response resp = commentsController.deleteComment(id, post_id, access_token);
		assertEquals(9002, resp.getStatus());		
	}
	
	/**
	 * Test delete failed 3
	 * 1002
	 * token expired
	 * 
	 * 
	 * */	
	@Test
	public void deleteTestFailed3() 
	{	
		String access_token = "token_expired";
		int id = 1;
		int post_id = 1;
		
		Response resp = commentsController.deleteComment(id, post_id, access_token);
		assertEquals(1002, resp.getStatus());		
	}
	
	/**
	 * Test delete failed 4
	 * 1003
	 * token invalid
	 * 
	 * */	
	@Test
	public void deleteTestFailed4() 
	{	
		String access_token = "token_invalid";
		int id = 1;
		int post_id = 1;
		
		Response resp = commentsController.deleteComment(id, post_id, access_token);
		assertEquals(1003, resp.getStatus());		
	}
	
	/**
	 * Test delete failed 5
	 * 3002
	 * Comment is not existed
	 * 
	 * */	
	@Test
	public void deleteTestFailed5() 
	{	
		String access_token = "abc123xyz";
		int id = 100;
		int post_id = 1;
		
		Response resp = commentsController.deleteComment(id, post_id, access_token);
		assertEquals(3002, resp.getStatus());		
	}
	
	/**
	 * Test delete failed 6
	 * 3004
	 * Comment does not belong to this post
	 * 
	 * */	
	@Test
	public void deleteTestFailed6() 
	{	
		String access_token = "abc123xyz";
		int id = 1;
		int post_id = 3;
		
		Response resp = commentsController.deleteComment(id, post_id, access_token);
		assertEquals(3004, resp.getStatus());		
	}
	
	/**
	 * Test delete failed 7
	 * 3003
	 * Comment does not belong to this user
	 * 
	 * */	
	@Test
	public void deleteTestFailed7() 
	{	
		String access_token = "missed_match_token";
		int id = 1;
		int post_id = 1;
		
		Response resp = commentsController.deleteComment(id, post_id, access_token);
		assertEquals(3003, resp.getStatus());		
	}
	
	/**
	 * Test delete failed 8
	 * 3007
	 * Delete comment failed
	 * 
	 * */	
	@Test
	public void deleteTestFailed8() 
	{	
		String access_token = "token5";
		int id = 5;
		int post_id = 5;
		
		Response resp = commentsController.deleteComment(id, post_id, access_token);
		assertEquals(3007, resp.getStatus());		
	}
	
	/**
	 * Test getAllCommentsForPost success
	 * 200
	 * 
	 * */	
	@Test
	public void getAllCommentsForPostTestSuccess()
	{
		int post_id = 1;
		Response resp = commentsController.getAllCommentsForPost(post_id);
		assertEquals(200, resp.getStatus());
	}
	
	/**
	 * Test getAllCommentsForPost failed 1
	 * 2504
	 * Post is not existed
	 * 
	 * */	
	@Test
	public void getAllCommentsForPostTestFailed1()
	{
		int post_id = 100;
		Response resp = commentsController.getAllCommentsForPost(post_id);
		assertEquals(2504, resp.getStatus());
	}
	
//	/**
//	 * Test getAllCommentsForPost failed 2
//	 * 3005
//	 * Get all comments for post failed
//	 * 
//	 * */	
//	@Test
//	public void getAllCommentsForPostTestFailed2()
//	{
//		int post_id = 3;
//		Response resp = commentsController.getAllCommentsForPost(post_id);
//		assertEquals(3005, resp.getStatus());
//	}
	
	/**
	 * Test getAllCommentsForUser success
	 * 200
	 * 
	 * */	
	@Test
	public void getAllCommentsForUserTestSuccess()
	{
		String access_token = "abc123xyz";
		Response resp = commentsController.getAllCommentsForUser(access_token);
		assertEquals(200, resp.getStatus());
	}
	
	/**
	 * Test getAllCommentsForUser failed 1
	 * 9002
	 * access_token null
	 * Missing token
	 * 
	 * */	
	@Test
	public void getAllCommentsForUserTestFailed1()
	{
		String access_token = null;
		Response resp = commentsController.getAllCommentsForUser(access_token);
		assertEquals(9002, resp.getStatus());
	}
	
	/**
	 * Test getAllCommentsForUser failed 2
	 * 1002
	 * access_token expired
	 * 
	 * */	
	@Test
	public void getAllCommentsForUserTestFailed2()
	{
		String access_token = "token_expired";
		Response resp = commentsController.getAllCommentsForUser(access_token);
		assertEquals(1002, resp.getStatus());
	}
	
	/**
	 * Test getAllCommentsForUser failed 3
	 * 1003
	 * access_token invalid
	 * 
	 * */	
	@Test
	public void getAllCommentsForUserTestFailed3()
	{
		String access_token = "token_invalid";
		Response resp = commentsController.getAllCommentsForUser(access_token);
		assertEquals(1003, resp.getStatus());
	}
	
	/**
	 * Test getAllCommentsForUser failed 4
	 * 3006
	 * Get all comments for user failed
	 * 
	 * */	
	@Test
	public void getAllCommentsForUserTestFailed4()
	{
		String access_token = "missed_match_token";
		Response resp = commentsController.getAllCommentsForUser(access_token);
		assertEquals(3006, resp.getStatus());
	}
	
}
