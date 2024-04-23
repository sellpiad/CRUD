package com.example.demo.repository;

import com.example.demo.repository.entity.Member;

public interface MemberRepository {

    String createMember();
    String updateMember();
    String deleteMember();
    Member getMember();

}
