package com.raushan.blog.serviceImp;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raushan.blog.entity.Comment;
import com.raushan.blog.entity.Post;
import com.raushan.blog.entity.User;
import com.raushan.blog.exception.ResourceNotFoundException;
import com.raushan.blog.payload.CommentDto;
import com.raushan.blog.repositories.CommentRepo;
import com.raushan.blog.repositories.PostRepo;
import com.raushan.blog.repositories.UserRepo;
import com.raushan.blog.service.CommentService;

@Service
public class CommentServiceImp implements CommentService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CommentDto createComent(CommentDto commentDto, Integer postId) {
		//User userEntity = userRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("User", "userId", userId));
		Post postEntity = postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "postId", postId));
		Comment commentEntity = modelMapper.map(commentDto, Comment.class);
		//commentEntity.setUser(userEntity);
		commentEntity.setPost(postEntity);
		Comment savedEntity = commentRepo.save(commentEntity);
		return modelMapper.map(savedEntity, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment commentEntity = commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "commentId", commentId));
		commentRepo.delete(commentEntity);

	}

}
