package com.landvibe.landlog.controller.form;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginForm {
    private final String email;
    private final String password;
}
