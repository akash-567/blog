package com.akash.projects.blog.models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {

	private int postId;

	@NotBlank
	@Min(5)
	private String title;

	@NotBlank
	@Min(5)
	private String content;

	private String imageName;

	private Date addedDate;

	private CategoryDto category;

	private UserDto user;

	private Set<CommentDto> comments = new HashSet<>();
}
