package com.landvibe.landlog.dto;

public class BlogForm {
    String title;
    String contents;

    public BlogForm(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return contents;
    }
}
