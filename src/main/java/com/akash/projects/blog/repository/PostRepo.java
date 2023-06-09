package com.akash.projects.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.akash.projects.blog.entities.Category;
import com.akash.projects.blog.entities.Post;
import com.akash.projects.blog.entities.User;

@Repository
public interface PostRepo extends JpaRepository<Post, Integer> {

	List<Post> findByUser(User user);

	List<Post> findByCategory(Category category);

	List<Post> findByTitleContaining(String title);

	@Query("select p from Post p where p.title like :key")
	List<Post> searchByKeywords(@Param("key") String title);
}
