package com.landvibe.landlog.repository;

import com.landvibe.landlog.domain.Blog;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.*;

@RequiredArgsConstructor
@Repository
public class DBBlogRepository implements BlogRepository{

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void save(Blog blog) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("blog").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("title", blog.getTitle());
        parameters.put("creatorId", blog.getCreatorId());
        parameters.put("content", blog.getContents());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        blog.setId(key.longValue());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("delete from blog where id = ?", "id");
    }

    @Override
    public Blog update(Long id, Blog blog) {
        jdbcTemplate.update("update blog set title = ?, content = ? where id = ? ", blog.getTitle(), blog.getContents());
        return blog;
    }

    @Override
    public Blog findById(Long id) {
        return jdbcTemplate.queryForObject("select * from blog where id = ?", blogRowMapper(), id);
    }

    @Override
    public List<Blog> findByCreatorId(Long creatorId) {
        List<Blog> result = jdbcTemplate.query("select * from blog where creatorId = ?", blogRowMapper(), creatorId);
        return result.stream().toList();
    }

    private RowMapper<Blog> blogRowMapper() {
        return (rs, rowNum) -> {
                Blog blog = new Blog();
                blog.setId(rs.getLong("id"));
                blog.setCreatorId(rs.getLong("creatorId"));
                blog.setTitle(rs.getString("title"));
                blog.setContents(rs.getString("content"));
                return blog;
        };
    }
}
