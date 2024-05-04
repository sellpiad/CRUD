package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.dto.authorDto.JwtToken;
import com.example.demo.service.dto.memberDto.CreateMemberRequest;
import com.example.demo.service.memberService.JwtTokenProvider;
import com.example.demo.service.memberService.MemberService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@AllArgsConstructor
@RestController
@Slf4j
public class UserController {

	private final MemberService memberService;
	private final JwtTokenProvider jwtTokenProvider;

	@PostMapping("/signup")
	public String signup(@RequestBody CreateMemberRequest createMemberRequest) {
		// TODO: process POST request

		String result = memberService.createMember(createMemberRequest);

		Logger.getLogger(result);

		return result;
	}

	@RequestMapping("/signin")
	@ResponseBody
	public ResponseEntity<Map<String, String>> signin(@RequestParam String username, String password,
			HttpServletResponse response) {
		// TODO: process POST request

		log.info("endpoint login entered");

		JwtToken jwtToken = memberService.signIn(username, password);

		// GrantType과 accessToken을 response body에 삽입
		HashMap<String, String> tokenForCli = new HashMap<>();

		tokenForCli.put("username", username);
		tokenForCli.put("grantType", jwtToken.getGrantType());
		tokenForCli.put("accessToken", jwtToken.getAccessToken());

		return new ResponseEntity<>(tokenForCli, HttpStatus.OK);
	}

	@RequestMapping("/userLogout")
	public String logout(@RequestHeader("Authorization") String token) {

		log.info("endpoint logout entered");

		if(jwtTokenProvider.deleteToken(token.substring(7)))
			return "logout";
		else
			return "failed";
	}
	

	@PostMapping("/userinfo")
	public String userInfo() {

		return new String();
	}

	@GetMapping("/refresh")
	@ResponseBody
	public ResponseEntity<Map<String, String>> refresh(@RequestHeader("Authorization") String token) {

		log.info("endpoint refresh entered");

		String accessToken = token.substring(7);
		JwtToken jwtToken = jwtTokenProvider.regenerateAccessToken(accessToken);

		if (jwtToken != null) {
			// GrantType과 accessToken을 response body에 삽입
			HashMap<String, String> tokenForCli = new HashMap<>();

			tokenForCli.put("username", jwtTokenProvider.getAuthentication(accessToken).getName());
			tokenForCli.put("grantType", jwtToken.getGrantType());
			tokenForCli.put("accessToken", jwtToken.getAccessToken());

			return new ResponseEntity<>(tokenForCli, HttpStatus.OK);

		} else{
			return null;
		}
	}

}
