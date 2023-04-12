package com.akash.projects.blog.repository;

import com.akash.projects.blog.entities.Category;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
