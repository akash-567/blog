package com.akash.projects.blog.services;

import com.akash.projects.blog.models.CommentDto;

public interface CommentService {

	CommentDto createComment(CommentDto commentDto, int postId);
	
	void deleteComment(int commentId);
}
