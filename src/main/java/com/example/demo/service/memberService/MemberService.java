package com.example.demo.service.memberService;

import com.example.demo.repository.entity.Member;
import com.example.demo.service.dto.authorDto.JwtToken;
import com.example.demo.service.dto.memberDto.CreateMemberRequest;


public interface MemberService {
    String createMember(CreateMemberRequest createMemberRequest);
    String udpateMember();
    String removeMember();
    Member findByMemberName(String memberName);
    JwtToken signIn(String username, String password);
}
