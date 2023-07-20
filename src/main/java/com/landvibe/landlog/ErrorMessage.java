package com.landvibe.landlog;

public enum ErrorMessage {
    WRONG_PASSWORD("WRONG_PASSWORD"),
    WRONG_EMAIL("WRONG_EMAIL"),
    EXIST_MEMBER("EXIST_MEMBER"),
    EMPTY_CREATOR_ID("EMPTY_CREATOR_ID"),
    EMPTY_BLOG_ID("EMPTY_BLOG_ID"),
    NO_MATCH_MEMBER_WITH_CREATOR_ID("NO_MATCH_MEMBER_WITH_CREATOR_ID"),
    NO_MATCH_BLOG_WITH_BLOG_ID("NO_MATCH_BLOG_WITH_BLOG_ID");

    public String message;

    ErrorMessage(String message) {
        this.message = message;
    }
}
