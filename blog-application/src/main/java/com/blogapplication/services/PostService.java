package com.blogapplication.services;

import java.util.List;

import com.blogapplication.entity.Post;
import com.blogapplication.entity.User;
import com.blogapplication.payloads.PostDto;

public interface PostService {

	public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);

	public PostDto updatePost(PostDto postDto, Integer postId);

	public void deletePost(Integer postId);

	public List<PostDto> getAllPosts();

	public PostDto getPostById(Integer postId);

	public List<PostDto> getAllPostByCategoryId(Integer categoryId);

	public List<PostDto> getAllPostByUserId(Integer userId);

	public List<PostDto> serchPostsByKeyword(String keyWord);
}
