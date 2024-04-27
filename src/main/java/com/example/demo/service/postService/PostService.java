package com.example.demo.service;

import java.util.List;

import com.example.demo.service.dto.CreatePostRequest;
import com.example.demo.service.dto.GetListRequest;
import com.example.demo.service.dto.GetPostRequest;
import com.example.demo.service.dto.LoginMemberRequest;
import com.example.demo.service.dto.UpdatePostRequest;

public interface PostService {
    
    boolean createPost(CreatePostRequest createPostRequest, LoginMemberRequest loginMemberRequest);
    boolean updatePost(UpdatePostRequest updatePostRequest, LoginMemberRequest loginMemberRequest);
    boolean deletePost(int id, LoginMemberRequest loginMemberRequest);
    GetPostRequest getPost(int id,LoginMemberRequest loginMemberRequest);
    List<GetListRequest> getList();

}
