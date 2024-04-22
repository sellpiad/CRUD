package com.example.demo.repository;

import java.util.List;

import com.example.demo.repository.entity.Post;

public interface PostRepository {

    String create(Post post);
    List<Post> findAll();
    Post findById(int id);
    String update(Post post);
    String delete(int id);

}
