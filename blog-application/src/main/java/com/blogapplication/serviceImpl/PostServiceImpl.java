package com.blogapplication.serviceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogapplication.entity.Category;
import com.blogapplication.entity.Post;
import com.blogapplication.entity.User;
import com.blogapplication.exceptions.ResourceNotFoundException;
import com.blogapplication.payloads.PostDto;
import com.blogapplication.repositories.CategoryRepository;
import com.blogapplication.repositories.PostRepository;
import com.blogapplication.repositories.UserRepository;
import com.blogapplication.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private CategoryRepository categoryRepo;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
		Category category = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

		Post postToSave = this.modelMapper.map(postDto, Post.class);
		postToSave.setContent(postDto.getContent());
		postToSave.setTitle(postDto.getTitle());
		postToSave.setImageName("default.png");
		postToSave.setCreatedOn(LocalDate.now());
		postToSave.setUser(user);
		postToSave.setCategory(category);
		Post savedPost = postRepo.save(postToSave);
		return this.modelMapper.map(savedPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post postToUpdate = postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));

		postToUpdate.setContent(postDto.getContent());
		postToUpdate.setTitle(postDto.getTitle());
		postToUpdate.setImageName(postDto.getImageName());
		Post updatedPost = postRepo.save(postToUpdate);
		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		// TODO Auto-generated method stub
		Post postToDelete = postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
		postRepo.delete(postToDelete);
	}

	@Override
	public List<PostDto> getAllPosts() {
		List<Post> savedPosts = postRepo.findAll();
		List<PostDto> allSavedPosts = savedPosts.stream()
				.map((allPosts) -> this.modelMapper.map(allPosts, PostDto.class)).collect(Collectors.toList());
		return allSavedPosts;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post savedPost = postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
		return this.modelMapper.map(savedPost, PostDto.class);
	}

	@Override
	public List<PostDto> getAllPostByCategoryId(Integer categoryId) {
		Category category = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
		List<Post> postByCategory = postRepo.findByCategory(category);
		return postByCategory.stream()
				.map((listofcategorypost) -> this.modelMapper.map(listofcategorypost, PostDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<PostDto> getAllPostByUserId(Integer userId) {
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

		List<Post> postsByUser = postRepo.findByUser(user);
		return postsByUser.stream().map((userPost) -> this.modelMapper.map(userPost, PostDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<PostDto> serchPostsByKeyword(String keyWord) {
		List<Post> searchedPost = postRepo.findByTitleContaining(keyWord);
		List<PostDto> collect = searchedPost.stream()
				.map((keywordSearch) -> this.modelMapper.map(keywordSearch, PostDto.class))
				.collect(Collectors.toList());

		return collect;

	}

}
