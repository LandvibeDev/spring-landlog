package com.landvibe.landlog.domain;

import com.landvibe.landlog.form.MemberForm;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
public class Member {

    private Long id;
    private String name;
    private String email;
    private String password;


}
