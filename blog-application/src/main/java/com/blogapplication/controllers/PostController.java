package com.blogapplication.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogapplication.payloads.PostDto;
import com.blogapplication.services.PostService;

@RestController
@RequestMapping("/posts")
public class PostController {

	@Autowired
	private PostService postService;

	@PostMapping("/users/{userId}/category/{categoryId}/createPost")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId,
			@PathVariable Integer categoryId) {
		PostDto post = postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(post, HttpStatus.OK);
	}

	@GetMapping("{postId}/getPosts")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) {
		PostDto postById = postService.getPostById(postId);
		return new ResponseEntity<PostDto>(postById, HttpStatus.OK);
	}

	@GetMapping("/")
	public ResponseEntity<List<PostDto>> getAllPost() {
		List<PostDto> allPosts = postService.getAllPosts();
		return new ResponseEntity<List<PostDto>>(allPosts, HttpStatus.OK);
	}

	@GetMapping("/user/{userId}/getposts")
	public ResponseEntity<List<PostDto>> getPostsByUserId(@PathVariable Integer userId) {
		List<PostDto> allPostByUserId = postService.getAllPostByUserId(userId);
		return new ResponseEntity<List<PostDto>>(allPostByUserId, HttpStatus.OK);
	}

	@GetMapping("/category/{categoryId}/getposts")
	public ResponseEntity<List<PostDto>> getPostsByCategoryId(@PathVariable Integer categoryId) {
		List<PostDto> allPostByCategoryId = postService.getAllPostByCategoryId(categoryId);
		return new ResponseEntity<List<PostDto>>(allPostByCategoryId, HttpStatus.OK);
	}

	@GetMapping("/{postId}/deletePost")
	public ResponseEntity<String> deletePost(@PathVariable Integer postId) {
		postService.deletePost(postId);
		return new ResponseEntity<String>("Post successfully deleted", HttpStatus.OK);
	}

	@GetMapping("/{keyword}/searchByKeyword")
	public ResponseEntity<List<PostDto>> searchByKeyword(@PathVariable String keyword) {
		List<PostDto> searchPostsByKeyword = postService.serchPostsByKeyword(keyword);
		return new ResponseEntity<List<PostDto>>(searchPostsByKeyword, HttpStatus.OK);
	}
}
