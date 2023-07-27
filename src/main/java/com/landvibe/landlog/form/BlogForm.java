package com.landvibe.landlog.form;

public class BlogForm {
    private final String title;
    private final String contents;

    public BlogForm(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }
}