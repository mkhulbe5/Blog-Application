package com.blogapplication.serviceImpl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.comments.CommentType;

import com.blogapplication.entity.Post;
import com.blogapplication.entity.UserComments;
import com.blogapplication.exceptions.ResourceNotFoundException;
import com.blogapplication.payloads.PostDto;
import com.blogapplication.payloads.UserCommentDto;
import com.blogapplication.repositories.PostRepository;
import com.blogapplication.repositories.UserCommentRepository;
import com.blogapplication.services.PostService;
import com.blogapplication.services.UserCommentService;

@Service
public class UserCommentServiceImpl implements UserCommentService {

	@Autowired
	private UserCommentRepository commentRepo;

	@Autowired
	private PostRepository postRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UserCommentDto postComment(UserCommentDto commentRequest, Integer postId) {

		Post post = postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
		UserComments createdComment = modelMapper.map(commentRequest, UserComments.class);
		createdComment.setPost(post);
		UserComments save = commentRepo.save(createdComment);
		UserCommentDto userCommentResponse = modelMapper.map(save, UserCommentDto.class);
		return userCommentResponse;
	}

	@Override
	public void deleteComment(int commentId) {
		UserComments userComments = new UserComments();
		commentRepo.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("User Comment", "commentId", commentId));
		commentRepo.delete(userComments);
	}

}
