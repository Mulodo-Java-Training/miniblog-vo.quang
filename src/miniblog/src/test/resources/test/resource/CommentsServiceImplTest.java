package test.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mulodo.miniblog.dao.CommentsDao;
import com.mulodo.miniblog.model.Comments;
import com.mulodo.miniblog.service.CommentsService;

@Service
public class CommentsServiceImplTest implements CommentsService 
{

	@Override
	public boolean isAddComment(Comments cmt) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isUpdateComment(Comments cmt) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Comments getComment(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isDeleteComment(Comments cmt) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Comments> getCommentsByPostId(int post_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Comments> getCommentsByUserId(int user_id) {
		// TODO Auto-generated method stub
		return null;
	}
}

