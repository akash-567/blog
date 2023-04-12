package com.akash.projects.blog.services;

import java.util.List;

import com.akash.projects.blog.models.CategoryDto;


public interface CategoryService {
	
	CategoryDto createCategory(CategoryDto categoryDto);
	
	CategoryDto updateCategory(CategoryDto categoryDto,int categoryId);
	
	void deleteCategory(int categoryId);
	
	CategoryDto getCategory(int categoryId);
	
	List<CategoryDto> getAllCategories();
} 
