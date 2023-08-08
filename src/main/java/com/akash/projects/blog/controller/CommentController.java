package com.akash.projects.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akash.projects.blog.models.ApiResponse;
import com.akash.projects.blog.models.CommentDto;
import com.akash.projects.blog.services.CommentService;

@RestController
@RequestMapping("/api")
public class CommentController {

	@Autowired
	private CommentService commentService;

	// post comment
	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,
			@PathVariable("postId") int postId) {

		CommentDto createdComment = this.commentService.createComment(commentDto, postId);

		return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
	}

	// delete comment
	@DeleteMapping("comments/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable("commentId") int commentId) {

		this.commentService.deleteComment(commentId);

		return new ResponseEntity<>(new ApiResponse("comment deleted successfully", true), HttpStatus.OK);
	}
}
