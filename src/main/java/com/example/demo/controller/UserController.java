package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.MemberService;
import com.example.demo.service.dto.CreateMemberRequest;
import com.example.demo.service.dto.LoginMemberRequest;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@AllArgsConstructor
@RestController
@Slf4j
public class UserController {

    private final MemberService memberService;

    @PostMapping("/signup")
	public String signup(@RequestBody CreateMemberRequest createMemberRequest) {
		//TODO: process POST request
		
		String result = memberService.createMember(createMemberRequest);

        Logger.getLogger(result);
        
		return result;
	}

	@RequestMapping("/userinfo")
	public ResponseEntity<Map<String,String>> login(@AuthenticationPrincipal(errorOnInvalidType = true) LoginMemberRequest loginMemberRequest) {
		//TODO: process POST request

		Map<String,String> user = new HashMap<>();

		user.put("id", loginMemberRequest.getUsername());
		user.put("createTime",loginMemberRequest.getCreateTime().toString());

		return new ResponseEntity<>(user, HttpStatus.OK);
		
	}


	
}
