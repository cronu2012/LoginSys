package com.example.loginsys.dao;

import com.example.loginsys.model.Member;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MemberDao {
     List<Member> getAll();

     Integer insert(Member member);
}
