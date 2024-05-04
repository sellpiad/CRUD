package com.example.demo.service.memberService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.repository.entity.Member;
import com.example.demo.repository.memberRepo.MemberRepositoryImpl;
import com.example.demo.service.dto.memberDto.LoginMemberRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class MemberSecurityService implements UserDetailsService{
    
    private final MemberRepositoryImpl memberRepositoryImpl;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = this.memberRepositoryImpl.findByMemberName(username);

        if(member==null)
            throw new UsernameNotFoundException("사용자가 입력한 아이디에 해당하는 사용자를 찾을 수 없습니다.");

        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        
        return new LoginMemberRequest(member.getId(),member.getMemberNum(), member.getPassword(), member.getCreateTime(), authorities);
        
    }
}