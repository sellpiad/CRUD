package com.example.demo.service.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class loginMemberRequest extends User{
  
    private String username;
    private String password;

    public loginMemberRequest(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);

        this.username = username;
        this.password = password;
        
        //TODO Auto-generated constructor stub
    }
   
}
