package test.resource;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mulodo.miniblog.model.Comments;
import com.mulodo.miniblog.model.Posts;
import com.mulodo.miniblog.model.Users;
import com.mulodo.miniblog.service.CommentsService;

@Service
public class CommentsServiceImplTest implements CommentsService 
{

	@Override
	public boolean isAddComment(Comments cmt) 
	{
		if (cmt.getPost().getId() == 1)
			return true;
		if (cmt.getPost().getId() == 3)					
			return false;
		else
			return false;
	}

	@Override
	public boolean isUpdateComment(Comments cmt) 
	{
		if (cmt.getId() == 1 && cmt.getPost().getId() == 1 && cmt.getUser().getId() == 1)
			return true;
		else
			return false;
	}

	@Override
	public Comments getComment(int id) 
	{
		switch(id) {
		case 1:
			Comments cmt1 = new Comments();
			cmt1.setId(1);
			cmt1.setContent("Content Of Comment");
			Posts post1 = new Posts();
			post1.setId(1);
			cmt1.setPost(post1);
			Users user1 = new Users();
			user1.setId(1);
			cmt1.setUser(user1);
			return cmt1;
		case 2:
			Comments cmt2 = new Comments();
			cmt2.setId(2);
			cmt2.setContent("Content Of Comment");
			Posts post2 = new Posts();
			post2.setId(2);
			cmt2.setPost(post2);
			Users user2 = new Users();
			user2.setId(2);
			cmt2.setUser(user2);
			return cmt2;
		case 3:
			Comments cmt3 = new Comments();
			cmt3.setId(3);
			cmt3.setContent("Content Of Comment");
			Posts post3 = new Posts();
			post3.setId(3);
			cmt3.setPost(post3);
			Users user3 = new Users();
			user3.setId(3);
			cmt3.setUser(user3);
			return cmt3;	
		case 4:
			Comments cmt4 = new Comments();
			cmt4.setId(4);
			cmt4.setContent("Content Of Comment");
			Posts post4 = new Posts();
			post4.setId(4);
			cmt4.setPost(post4);
			Users user4 = new Users();
			user4.setId(4);
			cmt4.setUser(user4);
			return cmt4;
		case 5:
			Comments cmt5 = new Comments();
			cmt5.setId(5);
			cmt5.setContent("Content Of Comment");
			Posts post5 = new Posts();
			post5.setId(5);
			cmt5.setPost(post5);
			Users user5 = new Users();
			user5.setId(5);
			cmt5.setUser(user5);
			return cmt5;
		default: return null;
		}
	}

	@Override
	public boolean isDeleteComment(Comments cmt) 
	{
		if (cmt.getId() == 4 && cmt.getPost().getId() == 4 && cmt.getUser().getId() == 4)
			return true;
		else
			return false;
	}

	@Override
	public List<Comments> getCommentsByPostId(Posts post) 
	{
		if (post.getId() == 1) {
			List<Comments> listComment = new ArrayList<Comments>();
			return listComment;
		}
		else	
			return null;
	}

	@Override
	public List<Comments> getCommentsByUserId(int user_id) 
	{
		if (user_id == 1){
			List<Comments> listComment = new ArrayList<Comments>();
			return listComment;
		}
		return null;
	}
}

