package com.raushan.blog.serviceImp;



import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.raushan.blog.entity.Category;
import com.raushan.blog.entity.Post;
import com.raushan.blog.entity.User;
import com.raushan.blog.exception.ResourceNotFoundException;
import com.raushan.blog.payload.PostDto;
import com.raushan.blog.payload.PostResponse;
import com.raushan.blog.repositories.CategoryRepo;
import com.raushan.blog.repositories.PostRepo;
import com.raushan.blog.repositories.UserRepo;
import com.raushan.blog.service.PostService;




@Service
public class PostServiceImp implements PostService{

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		User userEntity = userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "UserId", userId));
		Category categoryEntity = categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "CategoryId", categoryId));
		Post postEntity = modelMapper.map(postDto, Post.class);
		postEntity.setUser(userEntity);
		postEntity.setCategory(categoryEntity);
		postEntity.setDate(new Date());
		postEntity.setImage("Default.png");
		Post savedEntity = postRepo.save(postEntity);
		return modelMapper.map(savedEntity, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post postEntity = postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "postId", postId));
		postEntity.setTitle(postDto.getTitle());
		postEntity.setContent(postDto.getContent());
		postEntity.setImage(postDto.getImage());
		Post savedEntity = postRepo.save(postEntity);
		return modelMapper.map(savedEntity, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post postEntity = postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "postId", postId));
		postRepo.delete(postEntity);
		
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post postEntity = postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "postId", postId));
	    return modelMapper.map(postEntity, PostDto.class);
	}

	@Override
	public List<PostDto> getAllPost() {
		List<Post> allPostEntity = postRepo.findAll();
		List<PostDto> allPosts = allPostEntity.stream().map(post->modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return allPosts;	
	}

	@Override
	public List<PostDto> getAllPostByCategory(Integer categoryId) {
		Category categoryEntity = categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "CategoryId", categoryId));
		List<Post> postEntities = postRepo.findByCategory(categoryEntity);
		List<PostDto> allPostByCategory = postEntities.stream().map(posts->modelMapper.map(posts, PostDto.class)).collect(Collectors.toList());
		return allPostByCategory;
	}

	@Override
	public List<PostDto> getAllPostByUser(Integer userId) {
		User userEntity = userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "UserId", userId));
		List<Post> postEntities = postRepo.findByUser(userEntity);
		List<PostDto> allPostsByUser = postEntities.stream().map(post->modelMapper.map(post, PostDto.class)).toList();
		return allPostsByUser;
	}

//	@Override
//	public List<PostDto> searchPosts(String keyword) {
//		List<Post> postEntities = postRepo.findByTitleContainingIgnoreCaseAndContentContainingIgnoreCase(keyword);
//		List<PostDto> allSearchedPost = postEntities.stream().map(post->modelMapper.map(post, PostDto.class)).toList();
//		return allSearchedPost;
//	}
	
	@Override
	public List<PostDto> searchPosts(String keyword) {
		// TODO Auto-generated method stub
		List<Post> posts = this.postRepo.findByTitleContainingIgnoreCase(keyword);
		List<PostDto> postDtos = posts.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

		return postDtos;
	}
	
	
	//get all the post
	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		// TODO Auto-generated method stub
		Sort sort = null;
		if(sortDir.equalsIgnoreCase("asc")) {
			sort = Sort.by(sortBy).ascending();
		} else {
			sort = Sort.by(sortBy).descending();
		}
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		
		Page<Post> pagePost = this.postRepo.findAll(pageable);
		
		List<Post> allPosts = pagePost.getContent();
		
		List<PostDto> postDtos = allPosts.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastpage(pagePost.isLast());
		
		return postResponse;
	}

	


}
