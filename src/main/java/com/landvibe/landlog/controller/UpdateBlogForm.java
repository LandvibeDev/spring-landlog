package com.landvibe.landlog.controller;

public class UpdateBlogForm {
    private String title;
    private String contents;

    public UpdateBlogForm(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public UpdateBlogForm() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
