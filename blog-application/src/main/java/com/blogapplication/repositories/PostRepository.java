package com.blogapplication.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogapplication.entity.Category;
import com.blogapplication.entity.Post;
import com.blogapplication.entity.User;

public interface PostRepository extends JpaRepository<Post, Integer> {

	public List<Post> findByUser(User user);

	public List<Post> findByCategory(Category category);
	
	public List<Post> findByTitleContaining(String title);

	
}
