package com.akash.projects.blog.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {

	private int categoryId;
	
	@NotBlank
	@Size(min=4,message = "category title must be min 4 characters!!")
	private String categoryTitle;
	
	@NotBlank
	@Size(min=10,message = "category descripton must be min 10 characters!!")
	private String categoryDescription;
}
