package com.landvibe.landlog.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Builder
public class LoginForm {
    private String email;
    private String password;

    public LoginForm(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
