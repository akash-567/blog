package com.akash.projects.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.akash.projects.blog.entities.Comment;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
