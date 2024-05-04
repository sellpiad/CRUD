package com.example.demo.service.dto.memberDto;

import java.sql.Date;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;

@Getter
public class LoginMemberRequest extends User {

	private String username;
	private String password;
	private int userNum;
	private Date createTime;

	public LoginMemberRequest(String username, int userNum, String password, Date createTime,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);

		this.username = username;
		this.userNum = userNum;
		this.password = password;
		this.createTime = createTime;

		// TODO Auto-generated constructor stub
	}

}
