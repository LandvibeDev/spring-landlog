package com.landvibe.landlog.domain;

public class Member {

    private Long id;
    private String name;
    private String email;
    private String password;

    protected Member() {
    }

    public static Member createMember(String name, String email, String password) {
        Member member = new Member();
        member.setName(name);
        member.setEmail(email);
        member.setPassword(password);

        return member;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private void setName(String name) {
        this.name = name;
    }

    private void setEmail(String email) {
        this.email = email;
    }

    private void setPassword(String password) {
        this.password = password;
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
