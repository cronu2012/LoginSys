package com.example.loginsys.dao;

import com.example.loginsys.model.Member;
import org.springframework.stereotype.Component;

import java.util.List;


public interface MemberDao {
    List<Member> getAll();

    List<Member> getByConditions(String name, String email, String gender);

    List<Member> getByConditions(String name, String email);

    Member getById(Integer integer);

    Integer insert(Member member);
}
