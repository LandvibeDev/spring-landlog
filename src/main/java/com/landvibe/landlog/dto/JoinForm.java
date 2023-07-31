package com.landvibe.landlog.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class JoinForm {
    private String name;
    private String password;
    private String email;

    public JoinForm(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }
}
