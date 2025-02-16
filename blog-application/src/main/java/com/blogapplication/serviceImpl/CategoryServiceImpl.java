package com.blogapplication.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogapplication.entity.Category;
import com.blogapplication.exceptions.ResourceNotFoundException;
import com.blogapplication.payloads.CategoryDto;
import com.blogapplication.repositories.CategoryRepository;
import com.blogapplication.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category userToSave = this.modelMapper.map(categoryDto, Category.class);
		Category savedUser = this.categoryRepo.save(userToSave);
		return this.modelMapper.map(savedUser, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {

		Category categoryToUpdate = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category", "categoryId", categoryId));
		categoryToUpdate.setCategoryDescription(categoryDto.getCategoryDescription());
		categoryToUpdate.setCategoryTitle(categoryDto.getCategoryTitle());
		Category updatedCategory = this.categoryRepo.save(categoryToUpdate);
		return this.modelMapper.map(updatedCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto getCategoryById(Integer categoryId) {
		Category requestedCategory = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category", "categoryId", categoryId));
		return this.modelMapper.map(requestedCategory, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategories() {
		List<Category> allCategories = this.categoryRepo.findAll();
		List<CategoryDto> categoryDtoList = allCategories.stream()
				.map(categories -> this.modelMapper.map(categories, CategoryDto.class)).collect(Collectors.toList());
		return categoryDtoList;
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category categoryToDelete = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
		this.categoryRepo.delete(categoryToDelete);
	}
}
