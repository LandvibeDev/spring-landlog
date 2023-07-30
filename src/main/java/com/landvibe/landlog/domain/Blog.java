package com.landvibe.landlog.domain;

public class Blog {

    private Long id;
    private Long creatorId;
    private String title;
    private String contents;

    public Blog(Long creatorId, String title, String contents) {
        this.creatorId = creatorId;
        this.title = title;
        this.contents = contents;
    }

    public Blog(Long id, Long creatorId, String title, String contents) {
        this.id = id;
        this.creatorId = creatorId;
        this.title = title;
        this.contents = contents;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Long getId() {return id;}

    public Long getCreatorId() {
        return creatorId;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }
}
