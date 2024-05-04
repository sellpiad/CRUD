package com.example.demo.service.postService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.repository.entity.Post;
import com.example.demo.repository.postRepo.PostRepositoryImpl;
import com.example.demo.service.dto.memberDto.LoginMemberRequest;
import com.example.demo.service.dto.postDto.CreatePostRequest;
import com.example.demo.service.dto.postDto.GetListRequest;
import com.example.demo.service.dto.postDto.GetPostRequest;
import com.example.demo.service.dto.postDto.UpdatePostRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final PostRepositoryImpl postRepositoryImpl;
    
    @Override
    public boolean createPost(CreatePostRequest createPostRequest, LoginMemberRequest loginMemberRequest){
        
        Post post = Post.builder()
        .author(createPostRequest.getAuthor())
        .title(createPostRequest.getTitle())
        .content(createPostRequest.getContent())
        .build();

        return postRepositoryImpl.create(post,loginMemberRequest.getUserNum());
    }

    @Override
    public boolean updatePost(UpdatePostRequest updatePostRequest, LoginMemberRequest loginMemberRequest){

        if(loginMemberRequest != null && postRepositoryImpl.memberCheck(updatePostRequest.getId(), loginMemberRequest.getUserNum())){
           
            Post post = Post.builder()
            .id(updatePostRequest.getId())
            .title(updatePostRequest.getTitle())
            .author(updatePostRequest.getAuthor())
            .content(updatePostRequest.getContent())
            .build();
    
            return postRepositoryImpl.update(post);
        } else{


        }
            return false;
        
    }

    @Override
    public boolean deletePost(int id, LoginMemberRequest loginMemberRequest){

        if(loginMemberRequest != null && postRepositoryImpl.memberCheck(id, loginMemberRequest.getUserNum())){
            return postRepositoryImpl.delete(id);
        } else
            return false;
    }

    @Override
    public GetPostRequest getPost(int id,LoginMemberRequest loginMemberRequest){

        Post post = postRepositoryImpl.findById(id);

        if(loginMemberRequest != null && postRepositoryImpl.memberCheck(id,loginMemberRequest.getUserNum())){
            return GetPostRequest.builder()
            .id(post.getId())
            .author(post.getAuthor())
            .title(post.getTitle())
            .content(post.getContent())
            .createTime(post.getCreateTime())
            .isDeletable(true)
            .isEditable(true)
            .build();
        } else{
            return GetPostRequest.builder()
            .id(post.getId())
            .author(post.getAuthor())
            .title(post.getTitle())
            .content(post.getContent())
            .createTime(post.getCreateTime())
            .isDeletable(false)
            .isEditable(false)
            .build();
        }
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
