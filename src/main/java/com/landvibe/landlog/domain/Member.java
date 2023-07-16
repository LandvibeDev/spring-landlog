package com.landvibe.landlog.domain;

import com.landvibe.landlog.controller.MemberForm;

public class Member {

    private Long id;
    private String name;
    private String email;
    private String password;

    public Member() {
    }

    public Member(MemberForm memberForm) {
        this.name = memberForm.getName();
        this.email = memberForm.getEmail();
        this.password = memberForm.getPassword();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
