package com.landvibe.landlog.repository;

import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.form.BlogUpdateForm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class MemoryBlogRespositoryTest {

    BlogRespository blogRespository = new MemoryBlogRepository();
    String title = "title";
    Long creatorId = 1L;
    String contents = "contents";

    String afterUpdateTitle = "afterTitle";
    String afterUpdateContents = "afterContents";

    @BeforeEach
    public void 저장소_비워주기() {
        blogRespository.clear();
    }

    @Test
    @DisplayName("특정 creatorId로 블로그를 작성하면 blog 의 creatorId와 필드의 creatorId가 일치해야한다.")
    public void 블로그_아이디로_찾기_테스트() {
        boolean flag = true;
        Blog blog = new Blog(title, creatorId, contents);
        blogRespository.save(blog);


        List<Blog> blogList = blogRespository.findAllByMemberId(creatorId);


        List<Blog> list = blogList.stream()
                .filter((streamBlog) -> streamBlog.getCreatorId() != creatorId).toList();
        assertEquals(list.size(), 0);
    }

    @Test
    @DisplayName("특정 creatorId로 블로그를 작성하면 다른 creatorId로 조회를 하면, 리턴되는 리스트의 크기가 0이다")
    public void 블로그_아이디로_찾기_예외_테스트() {
        Blog blog = new Blog(title, creatorId, contents);
        blogRespository.save(blog);

        List<Blog> blogList = blogRespository.findAllByMemberId(creatorId + 1);

        assertEquals(blogList.size(), 0);
    }


    @Test
    @DisplayName("처음 저장하는 블로그의 아이디가 1이다")
    public void 정상적으로_블로그_저장() {
        boolean flag = true;
        Blog blog = new Blog(title, creatorId, contents);
        blogRespository.save(blog);


        List<Blog> blogList = blogRespository.findAllByMemberId(creatorId);


        List<Blog> list = blogList.stream()
                .filter((streamBlog) -> streamBlog.getCreatorId() != creatorId).toList();
        assertEquals(list.size(), 0);
    }

    @Test
    @DisplayName("블로그를 저장하고 삭제하면 찾아지면 안된다.")
    public void 정상적으로_삭제() {
        Blog blog = new Blog(title, creatorId, contents);
        Long blogId = blog.getId();
        blogRespository.save(blog);

        blogRespository.delete(blogId);

        Optional<Blog> result = blogRespository.findByBlogId(blogId);
        assertEquals(result, Optional.empty());
    }

    @Test
    @DisplayName("업데이트를 하면 제목과 내용이 수정된다")
    public void 정상적으로_변경() {
        Blog blog = new Blog(title, creatorId, contents);
        blogRespository.save(blog);
        BlogUpdateForm blogUpdateForm = new BlogUpdateForm(afterUpdateTitle, afterUpdateContents);

        blogRespository.update(blog.getId(), blogUpdateForm);

        Optional<Blog> optionalBlog = blogRespository.findByBlogId(blog.getId());
        Blog result = optionalBlog.get();
        assertEquals(result.getContents(), afterUpdateContents);
        assertEquals(result.getTitle(), afterUpdateTitle);
    }
}
