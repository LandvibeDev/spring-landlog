package com.landvibe.landlog.domain;

public class Blog {
    private Long id; // pk
    private String title;
    private String contents;
    private Long creatorId; // fk

    protected Blog() {
    }

    public static Blog createBlog(String title, String contents, Long creatorId) {
        Blog blog = new Blog();
        blog.setTitle(title);
        blog.setContents(contents);
        blog.setCreatorId(creatorId);
        return blog;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private void setTitle(String title) {
        this.title = title;
    }

    private void setContents(String contents) {
        this.contents = contents;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
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

    public Long getCreatorId() {
        return creatorId;
    }
}
