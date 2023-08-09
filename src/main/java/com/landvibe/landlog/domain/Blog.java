package com.landvibe.landlog.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Blog {

    private Long id;
    private Long creatorId;
    private String title;
    private String contents;

    @Builder
    public Blog(Long creatorId, String title, String contents) {
        this.creatorId = creatorId;
        this.title = title;
        this.contents = contents;
    }
}
