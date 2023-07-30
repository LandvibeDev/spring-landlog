package com.landvibe.landlog.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Blog {
    private Long creatorId;
    private Long id;
    private String title;
    private String contents;
}
