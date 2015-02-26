package test.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mulodo.miniblog.dao.PostsDao;
import com.mulodo.miniblog.model.Posts;
import com.mulodo.miniblog.service.PostsService;

@Service
public class PostsServiceImpleTest implements PostsService 
{

	@Override
	public boolean createPost(Posts post) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Posts getPostById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updatePost(Posts post) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deletePost(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Posts> getAllPosts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Posts> getPostsForUser(int user_id) {
		// TODO Auto-generated method stub
		return null;
	}

}

