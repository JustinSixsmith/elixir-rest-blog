package com.example.restblog.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    Post getPostsByCategory(String category);

}