package test.resource;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

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
			Posts post3 = new Posts();
			post3.setId(3);
			post3.setUser_id(3);
			return post3;
		case 4: 
			Posts post4 = new Posts();
			post4.setId(4);
			post4.setUser_id(4);
			return post4;
		case 5: 
			Posts post5 = new Posts();
			post5.setId(5);
			post5.setUser_id(5);
			return post5;
		
		default: return null;		
		}
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
	public boolean deletePost(int id) 
	{
		if (id == 1)
			return true;
		else
			return false;
	}

	@Override
	public List<Posts> getAllPosts() 
	{
		List<Posts> listPost = new ArrayList<Posts>();
		return listPost;
	}

	@Override
	public List<Posts> getPostsForUser(int user_id) 
	{
		if (user_id == 1) {
			List<Posts> listPost = new ArrayList<Posts>();		
			return listPost;
		}
		else
			return null;
	}

}

