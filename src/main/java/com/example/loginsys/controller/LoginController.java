package com.example.loginsys.controller;

import com.example.loginsys.constant.MemberGender;
import com.example.loginsys.model.Member;
import com.example.loginsys.service.MemberService;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Controller
public class LoginController {
    private static Logger logger = LoggerFactory.getLogger(LoginController.class);
    private static final String regexPsw = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,12}$";
    private static final String regexEm = "^\\w{1,63}@[a-zA-Z\\d]{2,63}\\.[a-zA-Z]{2,63}(\\.[a-zA-Z]{2,63})?$";

    private static final String regexZh = "[\\u4e00-\\u9fa5_a-zA-Z\\d]{3,8}";
    @Autowired
    private MemberService memberService;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

//    @GetMapping("/login")
//    public String loginPage() {
//        return "login";
//    }

    @GetMapping("/create")
    public String createPage() {
        return "create";
    }


    @PostMapping("/create/member")
    public String create(
            Model model,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam(name = "password_again") String passwordAgain,
            @RequestParam String name,
            @RequestParam String gender,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate birthday,
            @RequestParam(required = false) MultipartFile image1
    ) {

        List<String> errMsg = new ArrayList<>();
        String e = email.trim();
        String p = password.trim();
        String p2 = passwordAgain.trim();
        String n = name.trim();

        if (e == null || e.isEmpty()) {
            errMsg.add("????????????????????????!!");
        } else if (!e.matches(regexEm)) {
            errMsg.add("????????????????????????!!");
        }
        if (p == null || p.isEmpty()) {
            errMsg.add("??????????????????!!");
        } else if (!p.matches(regexPsw)) {
            errMsg.add("??????????????????!!");
        } else if (!p.equals(p2)) {
            errMsg.add("????????????????????????!!");
        }
        if (n == null || n.isEmpty()) {
            errMsg.add("??????????????????!!");
        } else if (!n.matches(regexZh)) {
            errMsg.add("??????????????????!!");
        }
        if (birthday == null) errMsg.add("?????????????????????!!");

        if (errMsg.size() != 0) {
            model.addAttribute("errMsg", errMsg);
            logger.info(model.toString());
            return "create";
        }
        Member member = new Member();
        member.setEmail(e);
        member.setPassword(p);
        member.setName(n);
        member.setGender(MemberGender.valueOf(gender));
        member.setBirthday(birthday);
        try {
            if (image1.getBytes().length == 0) {
                member.setImage1(null);
            } else {
                member.setImage1(image1.getBytes());
            }
        } catch (IOException ioException) {
            logger.error(ioException.getMessage());
        }

        Integer id = memberService.insert(member);
        if (id == null) {
            logger.info("????????????");
        } else {
            logger.info("?????????????????? id:" + id);
        }

        return "create";
    }

    @PostMapping("/login")
    public String login(
            Model model,
            @RequestParam String emailParam,
            @RequestParam String passwordParam
    ) {
        boolean validation = true;
        String email = emailParam.trim();
        String psw = passwordParam.trim();

        if (email.isEmpty() || email == null) {
            model.addAttribute("errorMsgForEmail", "????????????????????????!!");
            validation = false;
        }
        if (psw.isEmpty() || psw == null) {
            model.addAttribute("errorMsgForPsw", "??????????????????!!");
            validation = false;
        }
        if (!validation) {
            model.addAttribute("email", email);
            model.addAttribute("password", psw);
            logger.info(model.toString());
            return "login";
        }

        Member member = memberService.isMember(email, psw);
        if (member != null) {
            member.setPassword("");
            model.addAttribute("result", "????????????");
            model.addAttribute("member", member);
            logger.info(model.toString());
            return "index";
        } else {
            model.addAttribute("result", "????????????");
            model.addAttribute("errorMsgForEmail", "?????????????????????!!");
            model.addAttribute("email", email);
            model.addAttribute("password", psw);
            logger.info(model.toString());
            return "login";
        }
    }


}
