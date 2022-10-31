package com.example.loginsys.dao;

import com.example.loginsys.model.Member;
import com.example.loginsys.rowmapper.MemberRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.security.spec.NamedParameterSpec;
import java.sql.SQLException;
import java.util.*;


@Component
public class MemberDaoImpl implements MemberDao {
    private static Logger logger = LoggerFactory.getLogger(MemberDaoImpl.class);
    private static final String GET_ALL =
            "select id,name,email,password,gender,birthday,image1 from member";

    private static final String GET_ONE =
            "select id,name,email,password,gender,birthday,image1 from member where id = :id";

    private static final String GET_SOME_1 =
            "select id,name,email,password,gender,birthday,image1 " +
                    "from member where name = :name or email = :email or gender = :gender";

    private static final String GET_SOME_2 =
            "select id,name,email,password,gender,birthday,image1 " +
                    "from member where name = :name or email = :email and gender = :gender";


    private static final String INSERT =
            "insert into member (name,email,password,gender,birthday,image1) " +
                    "value (:name,:email,:password,:gender,:birthday,:image1)";


    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<Member> getAll() {
        Map<String, Object> map = new HashMap<>();

        List<Member> data = jdbcTemplate.query(GET_ALL, map, new MemberRowMapper());
        for (Member member : data) {
            member.setPassword(null);
        }

        return data;
    }


    @Override
    public List<Member> getByConditions(String name, String email, String gender) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("email", email);
        map.put("gender", gender);
        List<Member> list = null;
        if (name.equals("") && email.equals("")) {
            list = jdbcTemplate.query(GET_SOME_1, map, new MemberRowMapper());
        } else {
            list = jdbcTemplate.query(GET_SOME_2, map, new MemberRowMapper());
        }

        return list;
    }

    @Override
    public List<Member> getByConditions(String name, String email) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("email", email);
        map.put("gender", "");

        List<Member> list = jdbcTemplate.query(GET_SOME_1, map, new MemberRowMapper());

        return list;
    }

    @Override
    public Member getById(Integer integer) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", integer.toString());
        List<Member> members = jdbcTemplate.query(GET_ONE, map, new MemberRowMapper());
        logger.info(members.toString());
        if (members.size() != 0) {
            return members.get(0);
        } else {
            return null;
        }
    }


    @Override
    public Integer insert(Member member) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", member.getName());
        map.put("email", member.getEmail());
        map.put("password", member.getPassword());
        map.put("gender", member.getGender().name());
        map.put("birthday", member.getBirthday());
        map.put("image1", member.getImage1());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(INSERT, new MapSqlParameterSource(map), keyHolder);
        } catch (Exception sqlException) {
            logger.error(sqlException.getMessage());
            return null;
        }
        Integer id = keyHolder.getKey().intValue();

        return id;
    }
}
