package com.landvibe.landlog.form;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class MemberForm {
    private String name;
    private String email;
    private String password;
}
