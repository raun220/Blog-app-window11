package com.raushan.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raushan.blog.entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{

}
