package com.landvibe.landlog.controller.form;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BlogForm {
    private final String title;
    private final String contents;
}
