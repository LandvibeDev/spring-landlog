package com.landvibe.landlog.validator;

public enum ErrorMassage {
    NO_MATCH_MEMBERID_EXCEPTION("존재하지 않는 id 입니다."),
    DUPLICATE_NAME_SIGNUP_EXCEPTION("이미 존재하는 회원입니다."),
    DUPLICATE_EMAIL_SIGNUP_EXCEPTION("이미 존재하는 이메일입니다."),
    NO_EXIST_EMAIL_LOGIN_EXCEPTION("이메일을 확인해 주세요."),
    INCORRECT_PASSWORD_EXCEPTION("비밀번호를 확인해주세요."),
    NO_ONE_MEMBER_EXCEPTION("등록된 회원이 없습니다."),

    NO_MATCH_BLOGID_EXCEPTION("존재하지 않는 게시물 입니다."),
    NO_VALID_BLOG_TITLE("제목을 입력해주세요."),
    NO_VALID_BLOG_CONTENTS("내용을 입력해주세요.");


    private final String exceptionMessage;

    ErrorMassage(String exceptionMessage){
        this.exceptionMessage = exceptionMessage;
    }

    public String getMessage(){
        return this.exceptionMessage;
    }
}
