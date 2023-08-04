package com.landvibe.landlog.form;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoginForm {

    String email;
    String password;

}
