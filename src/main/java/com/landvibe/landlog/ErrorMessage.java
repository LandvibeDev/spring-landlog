package com.landvibe.landlog;

public enum ErrorMessage {
    WRONG_PASSWORD("비밀번호가 틀렸습니다"),
    NO_USER("해당 정보와 일치하는 회원이 존재하지 않습니다"),
    ALREADY_EXIST("이미 존재하는 회원입니다.");

    public String message;

    ErrorMessage(String message) {
        this.message = message;
    }
}
