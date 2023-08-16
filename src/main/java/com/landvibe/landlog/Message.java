package com.landvibe.landlog;

public enum Message {
    NO_INPUT_NAME("이름을 입력해주세요"),
    NO_INPUT_EMAIL("이메일을 입력해주세요"),
    NO_INPUT_PASSWORD("비밀번호를 입력해주세요"),
    NO_INPUT_TITLE("제목을 입력해주세요"),
    NO_INPUT_CONTENTS("내용을 입력해주세요"),
    NO_USER("존재하지 않는 회원입니다"),
    NO_BLOG("존재하지 않는 블로그 게시물입니다"),
    DUPLICATE_EMAIL("이미 존재하는 이메일입니다."),
    DUPLICATE_NAME("이미 존재하는 이름입니다.");

    public String message;

    Message(String message) {
        this.message = message;
    }
}
