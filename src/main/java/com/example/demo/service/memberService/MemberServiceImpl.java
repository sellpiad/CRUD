package com.example.demo.service.memberService;

import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.repository.entity.Member;
import com.example.demo.repository.memberRepo.MemberRepositoryImpl;
import com.example.demo.service.dto.authorDto.JwtToken;
import com.example.demo.service.dto.memberDto.CreateMemberRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final MemberRepositoryImpl memberRepositoryImpl;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    
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

    // JWT를 발급하여 로그인 처리.
    @Override
    public JwtToken signIn(String username, String password){

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        Optional<Authentication> authentication = Optional.ofNullable(authenticationManagerBuilder.getObject().authenticate(authenticationToken));

        JwtToken jwtToken =  jwtTokenProvider.generateToken(authentication.orElse(null));

        return jwtToken;
    
    }

    
}
