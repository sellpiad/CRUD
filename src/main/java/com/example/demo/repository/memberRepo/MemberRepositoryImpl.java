package com.example.demo.repository.memberRepo;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.repository.entity.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MemberRepositoryImpl implements MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public String createMember(Member member) {

        String query = "INSERT INTO member (id,password) VALUE("
                + "'" + member.getId() + "',"
                + "'" + member.getPassword() + "');";

        int result = this.jdbcTemplate.update(query);

        if (result == 1) {
            return "success";
        } else {
            return "failed";
        }

    }

    @Override
    public String updateMember(Member member) {

        String query = "UPDATE member SET "
                + "id = '" + member.getId() + "', "
                + "password = '" + member.getPassword() + "';";

        int result = this.jdbcTemplate.update(query);

        if (result == 0)
            return "failed";
        else {
            return "success";
        }
    }

    @Override
    public String deleteMember(Member member) {
        String query = "DELETE FROM board_1 WHERE ID = " + member.getId() + ";";

        return this.jdbcTemplate.update(query) + "";
    }

    @Override
    public Member findByMemberName(String memberName) throws EmptyResultDataAccessException{
        String query = "SELECT memberNum, id, password, createTime FROM member WHERE id=?;";

        return this.jdbcTemplate.queryForObject(query, memberMapper(), memberName);
    }

    private RowMapper<Member> memberMapper() {
        return(rs,rowNum) -> {
            return Member.builder()
            .memberNum(rs.getInt("memberNum"))
            .id(rs.getString("id"))
            .password(rs.getString("password"))
            .createTime(rs.getDate("createTime"))
            .build();
        };
    }

}
