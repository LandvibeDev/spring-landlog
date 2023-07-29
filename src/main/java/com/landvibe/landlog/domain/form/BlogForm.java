package com.landvibe.landlog.domain.form;

public class BlogForm {
    private Long id;
    private String title;
    private String contents;
    private Long creatorId;

    public BlogForm() {
    }

    public BlogForm(Long id, String title, String contents, Long creatorId) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.creatorId = creatorId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }
}