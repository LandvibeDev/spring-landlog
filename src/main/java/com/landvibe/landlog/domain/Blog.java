package com.landvibe.landlog.domain;

public class Blog {
    private Long id;
    private String title;
    private String contents;
    private Long creatorId;

    public Blog(String title, String contents, Long creatorId) {
        this.title = title;
        this.contents = contents;
        this.creatorId = creatorId;
    }

    public Blog() {}

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreatorId() {
        return creatorId;
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
