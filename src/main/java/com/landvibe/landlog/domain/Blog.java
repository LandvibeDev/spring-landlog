package com.landvibe.landlog.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Blog {

    private Long id;
    private String title;
    private Long creatorId;
    private String contents;


}