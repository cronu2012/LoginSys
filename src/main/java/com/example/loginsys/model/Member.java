package com.example.loginsys.model;

import com.example.loginsys.constant.MemberGender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    private Integer id;
    private String name;
    private String email;
    private String password;
    private MemberGender gender;
    private LocalDate birthday;
    private byte[] image1;
}
