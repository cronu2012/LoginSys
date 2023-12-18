package com.example.loginsys.controller;

import com.example.loginsys.model.Member;
import com.example.loginsys.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class  IndexController {
    private static Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private MemberService memberService;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/index/members/all")
    @ResponseBody
    public List<Member> all() {
        return memberService.queryAll();
    }

    @GetMapping("/index/members")
    @ResponseBody
    public List<Member> some(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String gender
    ) {
        return memberService.querySome(name, email, gender);
    }

    @GetMapping("/index/members/{id}")
    @ResponseBody
    public Member one(@PathVariable Integer id) {
        return memberService.queryOne(id);
    }


}
