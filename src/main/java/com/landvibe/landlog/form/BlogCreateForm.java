package com.landvibe.landlog.form;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BlogCreateForm {

    private String title;
    private String contents;

}