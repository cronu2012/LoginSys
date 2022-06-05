package com.example.loginsys.service;

import com.example.loginsys.model.Member;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MemberService {
    Member isMember(String email,String password);

    Integer insert(Member member);
}
