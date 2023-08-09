package com.landvibe.landlog.form;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
@Builder
public class BlogForm {
    private final String title;
    private final String contents;
}
