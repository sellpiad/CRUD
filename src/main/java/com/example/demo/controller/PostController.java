package com.example.demo.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.PostService;
import com.example.demo.service.dto.CreatePostRequest;
import com.example.demo.service.dto.GetListRequest;
import com.example.demo.service.dto.GetPostRequest;
import com.example.demo.service.dto.UpdatePostRequest;
import com.example.demo.service.dto.LoginMemberRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PostController {

	private final PostService postService;

	@GetMapping("/")
	public String main(@RequestParam String param) {
		return "hello!";
	}

	@GetMapping("/getList")
	public List<GetListRequest> getList() {
		return postService.getList();
	}

	@GetMapping("/getPost")
	public GetPostRequest getPost(@RequestParam int id,@AuthenticationPrincipal LoginMemberRequest loginMemberRequest) {

		return postService.getPost(id,loginMemberRequest);
	}

	@GetMapping("/deletePost")
	public String deletePost(@RequestParam int id,@AuthenticationPrincipal LoginMemberRequest loginMemberRequest) {

		if(postService.deletePost(id, loginMemberRequest)){
			return "success";
		}else{
			return "user info doesn't match at all.";
		}

	}

	@PostMapping("/updatePost")
	public String updatePost(@RequestBody UpdatePostRequest updatePostRequest,@AuthenticationPrincipal LoginMemberRequest loginMemberRequest) {

		if(postService.updatePost(updatePostRequest,loginMemberRequest)){
			return "success";
		}else{
			return "user info doesn't match at all.";
		}
	}

	@PostMapping("/createPost")
	public String createPost(@RequestBody CreatePostRequest createPostRequest,
			@AuthenticationPrincipal LoginMemberRequest loginMemberRequest) {

		if (loginMemberRequest != null)
			createPostRequest.setAuthor(loginMemberRequest.getUsername());
		else
			createPostRequest.setAuthor("guest");

		if(postService.createPost(createPostRequest,loginMemberRequest)){
			return "success";
		}else{
			return "user info doesn't match at all.";
		}
	}
}
