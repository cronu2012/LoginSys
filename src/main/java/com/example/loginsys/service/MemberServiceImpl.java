package com.example.loginsys.service;

import com.example.loginsys.dao.MemberDao;
import com.example.loginsys.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberDao memberDao;


    @Override
    public Member isMember(String email, String password) {
        Member member = null;
        List<Member> members = memberDao.getAll();
        for (Member m : members) {
            String emailData = m.getEmail();
            String passwordData = m.getPassword();
            if (email.equals(emailData) && password.equals(passwordData)) {
                member = m;
                break;
            }
        }
        return member;
    }

    @Override
    public List<Member> querySome(String name, String email, String gender) {
        if (gender.equals("")) {
            return memberDao.getByConditions(name, email);
        } else {
            return memberDao.getByConditions(name, email, gender);
        }
    }

    @Override
    public List<Member> queryAll() {
        return memberDao.getAll();
    }

    @Override
    public Member queryOne(Integer integer) {
        return memberDao.getById(integer);
    }

    @Override
    public Integer insert(Member member) {
        return memberDao.insert(member);
    }
}
