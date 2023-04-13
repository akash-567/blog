package com.akash.projects.blog.services;

import java.util.List;

import com.akash.projects.blog.entities.Post;
import com.akash.projects.blog.models.PostDto;

public interface PostService {

	PostDto createPost(PostDto postDto, int userId, int categoryId);

	PostDto updatePost(PostDto postDto, int postId);

	void deletePost(int postId);

	List<Post> getAllPosts();

	PostDto getPostsById(int postId);

	List<PostDto> getPostsByCategory(int categoryId);

	List<PostDto> getPostsByUser(int userId);

	List<PostDto> searchPosts(String keyword);
}
