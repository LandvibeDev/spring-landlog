package com.landvibe.landlog.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Blog {
    private Long id; // pk
    private String title;
    private String contents;
    private Long creatorId; // fk
}
