package com.example.demo.repository.memberRepo;

import com.example.demo.repository.entity.Member;

public interface MemberRepository {

    String createMember(Member member);
    String updateMember(Member member);
    String deleteMember(Member member);
    Member findByMemberName(String memberName);


}
