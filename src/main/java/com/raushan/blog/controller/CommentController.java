package com.raushan.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raushan.blog.payload.ApiResponse;
import com.raushan.blog.payload.CommentDto;
import com.raushan.blog.serviceImp.CommentServiceImp;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
public class CommentController {

	@Autowired
	private CommentServiceImp commentServiceImp;
	
	@PostMapping("/comment/{postId}")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,  @PathVariable("postId") Integer postid){
		CommentDto dto = commentServiceImp.createComent(commentDto, postid);
		return new ResponseEntity<CommentDto>(dto, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/deleteComment/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable("commentId") Integer commentId){
		commentServiceImp.deleteComment(commentId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Comment deleted sucessfully", true), HttpStatus.OK);
	}
}
