package com.example.demo.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.repository.entity.Member;
import com.example.demo.repository.memberRepo.MemberRepositoryImpl;
import com.example.demo.service.dto.memberDto.CreateMemberRequest;

@SpringBootTest()
public class MemberRepositoryImplTest {

    @Autowired
    private MemberRepositoryImpl memberRepositoryImpl;

    private CreateMemberRequest createMemberRequest;
    private Member member;

    @BeforeEach
    @DisplayName("환경 설정")
    void setup() {

        // given
        createMemberRequest = CreateMemberRequest.builder()
                .id("testID")
                .password("1004")
                .build();

        member = Member.builder()
                .id(createMemberRequest.getId())
                .password(createMemberRequest.getPassword())
                .build();

    }

    @Test
    @DisplayName("회원 생성")
    @Transactional
    void createMemberTest() throws Exception{

        // when
        String result = memberRepositoryImpl.createMember(member);

        // then

        Assertions.assertSame(result, "success");
    }
}
