package com.landvibe.landlog.form;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
@Builder
public class LoginForm {
    private final String email;
    private final String password;
}
