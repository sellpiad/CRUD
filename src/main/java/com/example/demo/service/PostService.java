package com.example.demo.service;

import java.util.List;

import com.example.demo.service.dto.CreatePostRequest;
import com.example.demo.service.dto.GetListRequest;
import com.example.demo.service.dto.GetPostRequest;
import com.example.demo.service.dto.UpdatePostRequest;

public interface PostService {
    
    String createPost(CreatePostRequest createPostRequest);
    String updatePost(UpdatePostRequest updatePostRequest);
    String deletePost(int id);
    GetPostRequest getPost(int id);
    List<GetListRequest> getList();

}
