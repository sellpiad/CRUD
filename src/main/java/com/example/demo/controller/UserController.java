package com.example.demo.controller;

import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.MemberService;
import com.example.demo.service.dto.CreateMemberRequest;

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
	public ResponseEntity<String> login(@AuthenticationPrincipal(errorOnInvalidType = true) UserDetails userDetails) {
		//TODO: process POST request

		return new ResponseEntity<String>(userDetails.getUsername(), HttpStatus.OK);
		
	}


	
}
