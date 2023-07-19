package com.landvibe.landlog.domain;

public class Blog {

	private Long blogId;
	private Long creatorId;
	private String title;
	private String contents;

	public Blog(Long blogId, Long creatorId, String title, String contents) {
		this.blogId = blogId;
		this.creatorId = creatorId;
		this.title = title;
		this.contents = contents;
	}

	public Long getBlogId() {
		return blogId;
	}

	public void setBlogId(Long blogId) {
		this.blogId = blogId;
	}

	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
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
