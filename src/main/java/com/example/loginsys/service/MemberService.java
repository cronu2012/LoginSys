package com.example.loginsys.service;

import com.example.loginsys.model.Member;
import org.springframework.stereotype.Component;

import java.util.List;


public interface MemberService {
    Member isMember(String email,String password);

    List<Member> queryByNameOrEmail(String name, String email);

    Integer insert(Member member);
}
