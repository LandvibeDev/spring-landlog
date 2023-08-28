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
public class DBBlogRepository implements BlogRepository {

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
        jdbcTemplate.update("delete from blog where id = ?", id);
    }

    @Override
    public Blog update(Long id, Blog blog) {
        jdbcTemplate.update("update blog set title = ?, content = ? where id = ? ", blog.getTitle(), blog.getContents(), id);
        return blog;
    }

    @Override
    public Optional<Blog> findById(Long id) {
        List<Blog> blogs = jdbcTemplate.query("select id, creatorId, title, content from blog where id = ?", blogRowMapper(), id);
        if (blogs.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(blogs.get(0));
    }

    @Override
    public List<Blog> findByCreatorId(Long creatorId) {
        List<Blog> result = jdbcTemplate.query("select id, creatorId, title, content from blog where creatorId = ?", blogRowMapper(), creatorId);
        return result;
    }

    @Override
    public void clear() {
        jdbcTemplate.update("truncate table blog");
    }

    private RowMapper<Blog> blogRowMapper() {
        return (rs, rowNum) ->
                Blog.builder()
                        .id(rs.getLong("id"))
                        .creatorId(rs.getLong("creatorId"))
                        .title(rs.getString("title"))
                        .contents(rs.getString("content"))
                        .build();
    }
}
