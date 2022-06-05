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
    private static final String SQL_GET_ALL = "select id,name,email,password,gender,birthday,image1 from member";

    private static final String SQL_INSERT =
            "insert into member (name,email,password,gender,birthday,image1) " +
                    "value (:name,:email,:password,:gender,:birthday,:image1)";


    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<Member> getAll() {
        Map<String, Object> map = new HashMap<>();

        List<Member> list = jdbcTemplate.query(SQL_GET_ALL, map, new MemberRowMapper());

        return list;
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
            jdbcTemplate.update(SQL_INSERT, new MapSqlParameterSource(map), keyHolder);
        } catch (Exception sqlException) {
            logger.error(sqlException.getMessage());
            return null;
        }
        Integer id = keyHolder.getKey().intValue();

        return id;
    }
}
