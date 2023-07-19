package com.landvibe.landlog.form;

public class BlogCreateForm {
    private String title;
    private String contents;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public BlogCreateForm(String title, String content) {
        this.title = title;
        this.contents = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }
}
