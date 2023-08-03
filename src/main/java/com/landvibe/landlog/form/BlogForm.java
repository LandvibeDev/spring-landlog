package com.landvibe.landlog.form;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BlogForm {
    private Long creatorId;
    private String title;
    private String contents;
}
