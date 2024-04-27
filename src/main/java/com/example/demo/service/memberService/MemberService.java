package com.example.demo.service;

import com.example.demo.repository.entity.Member;
import com.example.demo.service.dto.CreateMemberRequest;
import com.example.demo.service.dto.JwtToken;


public interface MemberService {
    String createMember(CreateMemberRequest createMemberRequest);
    String udpateMember();
    String removeMember();
    Member findByMemberName(String memberName);
    JwtToken signIn(String username, String password);
}
