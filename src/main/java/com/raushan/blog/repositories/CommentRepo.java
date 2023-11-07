package com.raushan.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raushan.blog.entity.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer>{

}
