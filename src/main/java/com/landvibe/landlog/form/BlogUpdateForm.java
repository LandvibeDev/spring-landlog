package com.landvibe.landlog.form;

public class BlogUpdateForm {

    private String title;
    private String contents;

    public BlogUpdateForm(String title, String content) {
        this.title = title;
        this.contents = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
