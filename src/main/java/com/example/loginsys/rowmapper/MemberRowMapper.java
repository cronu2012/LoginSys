package com.example.loginsys.rowmapper;

import com.example.loginsys.constant.MemberGender;
import com.example.loginsys.model.Member;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberRowMapper implements RowMapper<Member> {
    @Override
    public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
        Member member = new Member();
        member.setId(rs.getInt("id"));
        member.setName(rs.getString("name"));
        member.setPassword(rs.getString("password"));
        member.setEmail(rs.getString("email"));
        member.setGender(MemberGender.valueOf(rs.getString("gender")));
        member.setImage1(rs.getBytes("image1"));
        member.setBirthday(rs.getDate("birthday").toLocalDate());
        return member;
    }
}
