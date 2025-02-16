package com.blogapplication.services;

import java.util.List;

import com.blogapplication.payloads.CategoryDto;

public interface CategoryService {

	public CategoryDto createCategory(CategoryDto categoryDto);
	
	public CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);
	
	public CategoryDto getCategoryById(Integer categoryId);
	
	public List<CategoryDto> getAllCategories();
	
	public void deleteCategory(Integer categoryId);
}
