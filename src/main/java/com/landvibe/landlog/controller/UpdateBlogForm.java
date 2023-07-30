package com.landvibe.landlog.controller;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Builder
public class UpdateBlogForm {
    private String title;
    private String contents;
}
