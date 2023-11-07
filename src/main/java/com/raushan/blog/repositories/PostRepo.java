package com.raushan.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raushan.blog.entity.Category;
import com.raushan.blog.entity.Post;
import com.raushan.blog.entity.User;


public interface PostRepo extends JpaRepository<Post, Integer>{

	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);
	//List<Post> findByTitleContainingIgnoreCaseAndContentContainingIgnoreCase(String keyword);
	List<Post> findByTitleContainingIgnoreCaseAndContentContainingIgnoreCase(String title, String content);

	List<Post> findByTitleContainingIgnoreCase(String keyword);
}
