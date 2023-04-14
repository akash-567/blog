package com.akash.projects.blog.services;

import java.util.List;

import com.akash.projects.blog.models.PostDto;
import com.akash.projects.blog.models.PostResponse;

public interface PostService {

	PostDto createPost(PostDto postDto, int userId, int categoryId);

	PostDto updatePost(PostDto postDto, int postId);

	void deletePost(int postId);

	PostResponse getAllPosts(int pageNumber,int pageSize,String sortBy,String sortOrder);

	PostDto getPostsById(int postId);

	List<PostDto> getPostsByCategory(int categoryId);

	List<PostDto> getPostsByUser(int userId);

	List<PostDto> searchPosts(String keyword);
}
