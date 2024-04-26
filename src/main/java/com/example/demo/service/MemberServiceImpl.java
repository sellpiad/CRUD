package com.example.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.repository.MemberRepositoryImpl;
import com.example.demo.repository.entity.Member;
import com.example.demo.service.dto.CreateMemberRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepositoryImpl memberRepositoryImpl;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public String createMember(CreateMemberRequest createMemberRequest) {

        Member member = Member.builder()
        .id(createMemberRequest.getId())
        .password(passwordEncoder.encode(createMemberRequest.getPassword()))
        .build();

        String result = memberRepositoryImpl.createMember(member);

        return result;
    }

    @Override
    public String udpateMember() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'udpateMember'");
    }

    @Override
    public String removeMember() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeMember'");
    }

    @Override
    public Member findByMemberName(String memberName) {
    
        return memberRepositoryImpl.findByMemberName(memberName);
    }
    
}
