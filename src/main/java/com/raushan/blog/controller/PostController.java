package com.raushan.blog.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.raushan.blog.payload.PostDto;
import com.raushan.blog.payload.PostResponse;
import com.raushan.blog.serviceImp.FileServiceImp;
import com.raushan.blog.serviceImp.PostServiceImp;

import jakarta.servlet.http.HttpServletResponse;

import com.raushan.blog.payload.ApiResponse;


@RestController
public class PostController {
	
	@Autowired
	private PostServiceImp postService;
	
	@Autowired
	private FileServiceImp fileServiceImp;
	
	@Value("${project.image}")
	private String path;
	
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, 
			@PathVariable Integer userId, @PathVariable Integer categoryId) {
		
		PostDto createPost = postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<>(createPost, HttpStatus.CREATED);
	}
	
	// delete post
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<com.raushan.blog.payload.ApiResponse> deletePost(@PathVariable("postId") Integer postId){
		this.postService.deletePost(postId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Post deleted successfully", true), HttpStatus.OK);
	}
	
	// update
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable("postId") Integer postId) {
		PostDto pDto = this.postService.updatePost(postDto, postId);
		
		return new ResponseEntity<PostDto>(pDto, HttpStatus.OK);
	}
	
	// get all post by user
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable("userId") Integer userId){
		List<PostDto> postDtoList = this.postService.getAllPostByUser(userId);
		
		return new ResponseEntity<>(postDtoList, HttpStatus.OK);
	}
	
	// get all post by category
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostsBycategory(@PathVariable("categoryId") Integer categoryId) {
		List<PostDto> postDtoList = this.postService.getAllPostByCategory(categoryId);
		
		return new ResponseEntity<>(postDtoList, HttpStatus.OK);
	}
	
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable("postId") Integer postId){
		PostDto post = postService.getPostById(postId);
		return new ResponseEntity<PostDto>(post, HttpStatus.OK);
	}
	
	@GetMapping("/posts/")
	public ResponseEntity<List<PostDto>> getAllPost(){
		List<PostDto> post = postService.getAllPost();
		return new ResponseEntity<>(post, HttpStatus.OK);
	}
	
	@GetMapping("/Searchposts/{keyword}")
	public ResponseEntity<List<PostDto>> searchPostByKeyword(@PathVariable("keyword") String keyword){
		List<PostDto> allPostBykeyword = postService.searchPosts(keyword);
		return new ResponseEntity<List<PostDto>>(allPostBykeyword, HttpStatus.OK);
	}

	// get all posts
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPosts(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = "postId", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir){
		PostResponse postResponse = this.postService.getAllPost(pageNumber, pageSize, sortBy, sortBy);
		
		return new ResponseEntity<>(postResponse, HttpStatus.OK);
	}
	
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile multipartFile, @PathVariable("postId") Integer postId) throws IOException{
		PostDto postDto = postService.getPostById(postId);
		String fileName = fileServiceImp.uploadImage(path, multipartFile);
		postDto.setImage(fileName);
		PostDto updatePost = postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
	}
	
	@GetMapping(value = "post/downloadImage/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse httpServletResponse) throws IOException {
		InputStream inputStream = fileServiceImp.getResource(path, imageName);
		httpServletResponse.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(inputStream, httpServletResponse.getOutputStream());
	}
}
