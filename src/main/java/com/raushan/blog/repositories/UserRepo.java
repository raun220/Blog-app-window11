package com.raushan.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raushan.blog.entity.User;

public interface UserRepo extends JpaRepository<User, Integer>{

}
