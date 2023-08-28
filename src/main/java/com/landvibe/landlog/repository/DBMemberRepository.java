package com.landvibe.landlog.repository;

import com.landvibe.landlog.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class DBMemberRepository implements MemberRepository{

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Member save(Member member) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", member.getName());
        parameters.put("email", member.getEmail());
        parameters.put("password", member.getPassword());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        member.setId(key.longValue());
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject("select id, name, email, password from member where id = ?", memberRowMapper(), id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return Optional.ofNullable(jdbcTemplate.queryForObject("select id, name, email, password from member where name = ?", memberRowMapper(), name));
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return Optional.ofNullable(jdbcTemplate.queryForObject("select id, name, email, password from member where email = ?", memberRowMapper(), email));
    }

    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("select id, name, email, password from member", memberRowMapper());
    }

    @Override
    public void clear() {
        jdbcTemplate.update("truncate table member");
    }

    private RowMapper<Member> memberRowMapper() {
        return (rs, rowNum) -> Member.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .email(rs.getString("email"))
                .password(rs.getString("password"))
                .build();

    }
}
