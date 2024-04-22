package com.example.demo.controller;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

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

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BaseController {

    private final PostService postService;

	@GetMapping("/")
	public String main(@RequestParam String param) {
		return "hello!";
	}
	

	@GetMapping("/getList")
	public List<GetListRequest> getList() {

		Logger.getLogger("리스트 내놔! 리스트 내놔! 리스트 내놔!");
		
		return postService.getList();
	}

	@GetMapping("/getPost")
	public GetPostRequest getPost(@RequestParam int id){
		Logger.getLogger("포스트 내놔! 포스트 내놔! 포스트 내놔! 빼애애애애액!");
		return postService.getPost(id);
	}

	@GetMapping("/deletePost")
	public String deletePost(@RequestParam int id) {
		return postService.deletePost(id);
	}

	@PostMapping("/updatePost")
	public String updatePost(@RequestBody UpdatePostRequest updatePostRequest) {
		
		String result = postService.updatePost(updatePostRequest);

		if(result.equalsIgnoreCase("success")){
			return "success";
		} else {
			return "failed";
		}
	}
	
	@PostMapping("/createPost")
	public String createPost(@RequestBody CreatePostRequest createPostRequest) {

		String result = postService.createPost(createPostRequest);

		Logger.getLogger(createPostRequest.getAuthor());

		if(result.equalsIgnoreCase("success")){
			return "success";
		} else{
			return "failed";
		}
	}
}
