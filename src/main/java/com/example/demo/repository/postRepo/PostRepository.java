package com.example.demo.repository.postRepo;

import java.util.List;

import com.example.demo.repository.entity.Post;

public interface PostRepository {

    boolean create(Post post,int memberNum);
    List<Post> findAll();
    Post findById(int id);
    boolean update(Post post);
    boolean delete(int id);
    boolean memberCheck(int postNum, int memberNum);

}
