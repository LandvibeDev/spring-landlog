package com.landvibe.landlog.form;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MemberLoginForm {
    private String email;
    private String password;
}
