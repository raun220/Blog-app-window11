package com.raushan.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raushan.blog.payload.ApiResponse;
import com.raushan.blog.payload.CategoryDto;
import com.raushan.blog.serviceImp.CategoryServiceImp;

@RestController
@RequestMapping("/app/category/")
public class CategoryController {

	@Autowired
	private CategoryServiceImp serviceImp;
	
	@PostMapping("/createCategory")
	ResponseEntity<CategoryDto> createCategoryController(@RequestBody CategoryDto categoryDto){
		CategoryDto dto = serviceImp.careteCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(dto, HttpStatus.CREATED);
	}
	
	@PutMapping("/{categoryId}")
	ResponseEntity<CategoryDto> updateCategoryController(@RequestBody CategoryDto categoryDto, @PathVariable("categoryId") Integer categoryId){
		CategoryDto dto = serviceImp.updateCategory(categoryDto, categoryId);
		return new ResponseEntity<CategoryDto>(dto, HttpStatus.OK);
	}
	
	@GetMapping("/{categoryId}")
	ResponseEntity<CategoryDto> getCategoryController( @PathVariable("categoryId") Integer categoryId){
		CategoryDto dto = serviceImp.getCategoryById(categoryId);
		return new ResponseEntity<CategoryDto>(dto, HttpStatus.OK);
	}
	
	@GetMapping("/getAllCategory")
	ResponseEntity<List<CategoryDto>> getAllCategoryController( ){
		List<CategoryDto> dto = serviceImp.getAllCategory();
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	@DeleteMapping("/{categoryId}")
	ResponseEntity<ApiResponse> deletecategory(@PathVariable("categoryId") Integer categoryId){
		serviceImp.deleteCategory(categoryId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("category Delete successfully", true), HttpStatus.OK);
	}
	
	
	
	
}
