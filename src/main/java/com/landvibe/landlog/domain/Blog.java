package com.landvibe.landlog.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Blog {

    private Long id;
    private String title;
    private String contents;
    private Long creatorId;

    public Blog() {
    }

    public Blog(Long id, String title, String contents, Long creatorId) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.creatorId = creatorId;
    }
}
