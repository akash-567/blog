package com.akash.projects.blog.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.akash.projects.blog.config.AppConstants;
import com.akash.projects.blog.models.ApiResponse;
import com.akash.projects.blog.models.PostDto;
import com.akash.projects.blog.models.PostResponse;
import com.akash.projects.blog.services.FileService;
import com.akash.projects.blog.services.PostService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private FileService fileService;

	@Value("${project.image}")
	private String path;

	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable int userId,
			@PathVariable int categoryId) {
		PostDto createdPostDto = this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<>(createdPostDto, HttpStatus.CREATED);
	}

	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable int postId) {
		PostDto updatedPostDto = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<>(updatedPostDto, HttpStatus.OK);
	}

	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_ORDER, required = false) String sortOrder) {
		PostResponse postResponse = this.postService.getAllPosts(pageNumber, pageSize, sortBy, sortOrder);
		return new ResponseEntity<>(postResponse, HttpStatus.OK);
	}

	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable int postId) {
		PostDto postDto = this.postService.getPostsById(postId);
		return new ResponseEntity<>(postDto, HttpStatus.OK);
	}

	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable int categoryId) {
		List<PostDto> postDtoList = this.postService.getPostsByCategory(categoryId);
		return new ResponseEntity<>(postDtoList, HttpStatus.OK);
	}

	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable int userId) {
		List<PostDto> postDtoList = this.postService.getPostsByUser(userId);
		return new ResponseEntity<>(postDtoList, HttpStatus.OK);
	}

	@DeleteMapping("/posts/{postID}")
	public ResponseEntity<ApiResponse> deletePostById(@PathVariable int postID) {
		this.postService.deletePost(postID);
		return new ResponseEntity<>(new ApiResponse("post deleted successfully", true), HttpStatus.OK);
	}

	// search using Keyword
	@GetMapping("posts/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable String keywords) {
		List<PostDto> postDtos = this.postService.searchPosts(keywords);
		return new ResponseEntity<>(postDtos, HttpStatus.OK);
	}

	// upload image in post
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadImage(@RequestParam("image") MultipartFile image,
			@PathVariable("postId") int postId) throws IOException {

		PostDto dto = this.postService.getPostsById(postId);
		String fileName = this.fileService.uploadImage(path, image);

		dto.setImageName(fileName);
		PostDto updatedPost = this.postService.updatePost(dto, postId);

		return new ResponseEntity<>(updatedPost, HttpStatus.OK);
	}
	
	// method to serve image files
		@GetMapping(value = "/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
		public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response)
				throws IOException {

			InputStream resource = this.fileService.getResource(path, imageName);
			response.setContentType(MediaType.IMAGE_JPEG_VALUE);
			StreamUtils.copy(resource, response.getOutputStream());
		}

}
