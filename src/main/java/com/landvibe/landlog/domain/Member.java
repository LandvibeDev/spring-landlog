package com.landvibe.landlog.domain;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Member {

    private Long id;
    private String name;
    private String email;
    private String password;

}
