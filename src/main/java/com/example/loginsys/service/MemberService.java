package com.example.loginsys.service;

import com.example.loginsys.model.Member;
import org.springframework.stereotype.Component;

import java.util.List;


public interface MemberService {
    Member isMember(String email, String password);

    List<Member> querySome(String name, String email, String gender);

    List<Member> queryAll();

    Member queryOne(Integer integer);

    Integer insert(Member member);
}
