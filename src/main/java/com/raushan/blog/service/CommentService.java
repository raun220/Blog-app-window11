package com.raushan.blog.service;

import com.raushan.blog.payload.CommentDto;

public interface CommentService {

	CommentDto createComent(CommentDto commentDto, Integer postId);
	void deleteComment(Integer commentId);
}
