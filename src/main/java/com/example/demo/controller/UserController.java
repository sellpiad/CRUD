package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.dto.authorDto.JwtToken;
import com.example.demo.service.dto.memberDto.CreateMemberRequest;
import com.example.demo.service.memberService.MemberService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
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

	@RequestMapping("/signin")
	@ResponseBody
	public ResponseEntity<Map<String,String>> signin(@RequestParam String username, String password, HttpServletResponse response) {
		//TODO: process POST request

		JwtToken jwtToken = memberService.signIn(username,password);

		Cookie cookie = new Cookie("refreshToken", jwtToken.getRefreshToken());

		cookie.setMaxAge(7 * 24 * 60 * 60);

		cookie.setSecure(true);
		cookie.setHttpOnly(true);
		cookie.setPath("/");
		
		response.addCookie(cookie);

		HashMap<String,String> tokenForCli = new HashMap<>();

		tokenForCli.put("grantType", jwtToken.getGrantType());
		tokenForCli.put("accessToken", jwtToken.getAccessToken());
		
		return new ResponseEntity<>(tokenForCli,HttpStatus.OK);
	}

	@GetMapping("/userInfo")
	public String userInfo(@RequestParam String param) {

		return new String();
	}
	


	
}
