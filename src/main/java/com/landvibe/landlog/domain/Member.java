package com.landvibe.landlog.domain;

import lombok.*;

@Setter @Getter
public class Member {

    private Long id;
    private String name;
    private String email;
    private String password;

    @Builder
    public Member(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
