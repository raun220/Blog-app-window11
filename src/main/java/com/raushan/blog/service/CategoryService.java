package com.raushan.blog.service;

import java.util.List;

import com.raushan.blog.payload.CategoryDto;

public interface CategoryService {

	CategoryDto careteCategory(CategoryDto categoryDto);
	CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);
	void deleteCategory(Integer categoryId);
	CategoryDto getCategoryById(Integer categoryId);
	List<CategoryDto> getAllCategory();
	
}
