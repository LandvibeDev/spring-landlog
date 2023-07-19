package com.landvibe.landlog.domain;

public class Blog {

    private Long id;
    private String title;

    private Long creatorId;
    private String contents;

    public Blog(String title, Long memberId, String content) {
        this.title = title;
        this.creatorId = memberId;
        this.contents = content;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public String getContents() {
        return contents;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
