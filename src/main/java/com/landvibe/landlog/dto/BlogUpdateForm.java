package com.landvibe.landlog.dto;

public class BlogUpdateForm {
    Long id;
    String title;
    String contents;

    public BlogUpdateForm(Long id, String title, String contents) {
        this.id = id;
        this.title = title;
        this.contents = contents;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }
}
