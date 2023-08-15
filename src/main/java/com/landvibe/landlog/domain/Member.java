package com.landvibe.landlog.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Member {
    private Long id;
    private String name;
    private String email;
    private String password;
}
