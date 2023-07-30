package com.landvibe.landlog.controller;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateBlogForm {
    private String title;
    private String contents;
}
