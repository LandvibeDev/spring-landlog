package com.landvibe.landlog.controller;

import lombok.Data;
import lombok.Getter;

@Getter
public class MemberForm {
    private String name;
    private String email;
    private String password;
}
