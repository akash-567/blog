package com.akash.projects.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akash.projects.blog.entities.Comment;
import com.akash.projects.blog.entities.Post;
import com.akash.projects.blog.exceptions.ResourceNotFoundException;
import com.akash.projects.blog.models.CommentDto;
import com.akash.projects.blog.repository.CommentRepo;
import com.akash.projects.blog.repository.PostRepo;
import com.akash.projects.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepo commentRepo;

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private ModelMapper mapper;

	@Override
	public CommentDto createComment(CommentDto commentDto, int postId) {

		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));

		Comment comment = this.mapper.map(commentDto, Comment.class);
		comment.setPost(post);
		Comment savedComment = this.commentRepo.save(comment);
		return this.mapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(int commentId) {
		Comment comment = this.commentRepo.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "commentId", commentId));

		this.commentRepo.delete(comment);
	}

}
