package com.akash.projects.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.akash.projects.blog.entities.Category;
import com.akash.projects.blog.entities.Post;
import com.akash.projects.blog.entities.User;
import com.akash.projects.blog.exceptions.ResourceNotFoundException;
import com.akash.projects.blog.models.PostDto;
import com.akash.projects.blog.models.PostResponse;
import com.akash.projects.blog.repository.CategoryRepo;
import com.akash.projects.blog.repository.PostRepo;
import com.akash.projects.blog.repository.UserRepo;
import com.akash.projects.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private ModelMapper modelMappper;

	@Override
	public PostDto createPost(PostDto postDto, int userId, int categoryId) {

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

		Post post = this.modelMappper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(cat);

		return this.modelMappper.map(this.postRepo.save(post), PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, int postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "post Id", postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
//		this.postRepo.save(post);
		return this.modelMappper.map(this.postRepo.save(post), PostDto.class);
	}

	@Override
	public void deletePost(int postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "post Id", postId));
		this.postRepo.delete(post);
	}

	@Override
	public PostResponse getAllPosts(int pageNumber, int pageSize, String sortBy, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		Pageable p = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> pagePost = this.postRepo.findAll(p);
		List<Post> allPosts = pagePost.getContent();
		List<PostDto> postDtos = allPosts.stream().map(post -> this.modelMappper.map(post, PostDto.class))
				.collect(Collectors.toList());

		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());

		return postResponse;
	}

	@Override
	public PostDto getPostsById(int postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "post Id", postId));

		return this.modelMappper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getPostsByCategory(int categoryId) {
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));
		List<Post> postList = this.postRepo.findByCategory(cat);
		return postList.stream().map(post -> this.modelMappper.map(post, PostDto.class)).collect(Collectors.toList());
	}

	@Override
	public List<PostDto> getPostsByUser(int userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "user id", userId));
		List<Post> postList = this.postRepo.findByUser(user);
		return postList.stream().map(post -> this.modelMappper.map(post, PostDto.class)).collect(Collectors.toList());
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		List<Post> postsList = this.postRepo.findByTitleContaining(keyword);
		return postsList.stream().map(post -> this.modelMappper.map(post, PostDto.class)).collect(Collectors.toList());
	}

}
