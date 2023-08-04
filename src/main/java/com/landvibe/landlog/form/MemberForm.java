package com.landvibe.landlog.form;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MemberForm {

    private String name;
    private String email;
    private String password;

}
