package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.repository.PostRepositoryImpl;
import com.example.demo.repository.entity.Post;
import com.example.demo.service.dto.CreatePostRequest;
import com.example.demo.service.dto.GetListRequest;
import com.example.demo.service.dto.GetPostRequest;
import com.example.demo.service.dto.UpdatePostRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final PostRepositoryImpl postRepositoryImpl;
    
    @Override
    public String createPost(CreatePostRequest createPostRequest){
        
        Post post = Post.builder()
        .author(createPostRequest.getAuthor())
        .title(createPostRequest.getTitle())
        .content(createPostRequest.getContent())
        .build();

        return postRepositoryImpl.create(post);
    }

    @Override
    public String updatePost(UpdatePostRequest updatePostRequest){

        Post post = Post.builder()
        .id(updatePostRequest.getId())
        .title(updatePostRequest.getTitle())
        .author(updatePostRequest.getAuthor())
        .content(updatePostRequest.getContent())
        .build();

        return postRepositoryImpl.update(post);

    }

    @Override
    public String deletePost(int id){
        return postRepositoryImpl.delete(id);
    }

    @Override
    public GetPostRequest getPost(int id){

        Post post = postRepositoryImpl.findById(id);

        return GetPostRequest.builder()
        .id(post.getId())
        .author(post.getAuthor())
        .title(post.getTitle())
        .content(post.getContent())
        .createTime(post.getCreateTime())
        .build();
    }

    @Override
    public List<GetListRequest> getList(){

        List<Post> postList = postRepositoryImpl.findAll();
        List<GetListRequest> requestList = new ArrayList<>();

        for(Post post:postList){
            
            GetListRequest request = GetListRequest.builder()
            .id(post.getId())
            .author(post.getAuthor())
            .title(post.getTitle())
            .createTime(post.getCreateTime())
            .build();

            requestList.add(request);

        }

        return requestList;
    }
}
