package com.raushan.blog.serviceImp;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raushan.blog.entity.Category;
import com.raushan.blog.exception.ResourceNotFoundException;
import com.raushan.blog.payload.CategoryDto;
import com.raushan.blog.repositories.CategoryRepo;
import com.raushan.blog.service.CategoryService;

@Service
public class CategoryServiceImp implements CategoryService{

	@Autowired
	private CategoryRepo repo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CategoryDto careteCategory(CategoryDto categoryDto) {
		Category entity = modelMapper.map(categoryDto, Category.class);
		Category savedEntity = repo.save(entity);
		
		return modelMapper.map(savedEntity, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		Category entity = repo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category", "CategoryId", categoryId));
		entity.setCategoryTitle(categoryDto.getCategoryTitle());
		entity.setCategoryDescription(categoryDto.getCategoryDescription());
		Category savedEntity = repo.save(entity);
		
		return modelMapper.map(savedEntity, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category entity = repo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category", "CategoryId", categoryId));
		repo.delete(entity);
		
	}

	@Override
	public CategoryDto getCategoryById(Integer categoryId) {
		Category entity = repo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category", "CategoryId", categoryId));
		
		return modelMapper.map(entity, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category> allEntitys = repo.findAll();
	    List<CategoryDto> allCategory =	allEntitys.stream().map(cat->modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
		return allCategory;
	}

}
