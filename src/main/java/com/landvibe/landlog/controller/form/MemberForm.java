package com.landvibe.landlog.controller.form;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberForm {
    private final String name;
    private final String email;
    private final String password;
}
