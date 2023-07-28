package com.landvibe.landlog.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class BlogMemberRepositoryTest {
    MemoryBlogRepository repository = new MemoryBlogRepository();

    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

}
