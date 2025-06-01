package com.blogapplication.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogapplication.payloads.UserCommentDto;
import com.blogapplication.services.UserCommentService;

@RestController
@RequestMapping("/api")
public class UserCommentController {

	@Autowired
	private UserCommentService commentService;

	@PostMapping("/postComment/{postId}")
	public ResponseEntity<UserCommentDto> postComment(@RequestBody UserCommentDto commentReq,
			@PathVariable Integer postId) {
		UserCommentDto postComment = commentService.postComment(commentReq, postId);
		return new ResponseEntity<UserCommentDto>(postComment, HttpStatus.CREATED);
	}

	@PostMapping("/deleteComment/{commentId}")
	public ResponseEntity<String> deleteComment(@PathVariable Integer commentId) {
		commentService.deleteComment(commentId);
		return new ResponseEntity<String>("Post deleted successfull", HttpStatus.OK);

	}
}
