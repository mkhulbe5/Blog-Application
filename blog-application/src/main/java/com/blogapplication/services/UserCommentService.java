package com.blogapplication.services;

import com.blogapplication.payloads.UserCommentDto;

public interface UserCommentService {
	public UserCommentDto postComment(UserCommentDto commentRequest, Integer postId);

	public void deleteComment(int commentId);
}
