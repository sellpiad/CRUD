package com.example.demo.service.postService;

import java.util.List;

import com.example.demo.service.dto.memberDto.LoginMemberRequest;
import com.example.demo.service.dto.postDto.CreatePostRequest;
import com.example.demo.service.dto.postDto.GetListRequest;
import com.example.demo.service.dto.postDto.GetPostRequest;
import com.example.demo.service.dto.postDto.UpdatePostRequest;

public interface PostService {
    
    boolean createPost(CreatePostRequest createPostRequest, LoginMemberRequest loginMemberRequest);
    boolean updatePost(UpdatePostRequest updatePostRequest, LoginMemberRequest loginMemberRequest);
    boolean deletePost(int id, LoginMemberRequest loginMemberRequest);
    GetPostRequest getPost(int id,LoginMemberRequest loginMemberRequest);
    List<GetListRequest> getList();

}
