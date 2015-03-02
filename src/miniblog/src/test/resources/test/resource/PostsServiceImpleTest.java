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
	public boolean createPost(Posts post) 
	{
		if (post.getTitle() == "Title" &&
			post.getDescription() == "Description" &&
			post.getContent() == "Content")
			return true;
		else
			return false;
	}

	@Override
	public Posts getPostById(int id) 
	{
		switch (id) {
		case 1:
			Posts post = new Posts();
			post.setId(1);
			post.setTitle("Title");
			post.setDescription("Description");
			post.setContent("Content");
			post.setUser_id(1);
			return post;
		case 2: return null;
		case 3: 
			Posts post1 = new Posts();
			post1.setUser_id(3);
			return post1;
		
		default: return null;
		
		}
		
//		if (id == 1) {
//			Posts post = new Posts();
//			post.setId(1);
//			post.setTitle("Title");
//			post.setDescription("Description");
//			post.setContent("Content");
//			post.setUser_id(1);
//			return post;
//		}
//		else	
//			return null;
	}

	@Override
	public boolean updatePost(Posts post) 
	{
		if (post.getId() == 1)
			return true;
		else
			if(post.getId() == 3)
				return false;
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

