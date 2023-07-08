package com.natsukashiiz.iiserverapi.model.response;

public class BookmarkResponse {
    private Long id;
    private BlogResponse blog;

    public Long getId() {
        return id;
    }

    public BookmarkResponse setId(Long id) {
        this.id = id;
        return this;
    }

    public BlogResponse getBlog() {
        return blog;
    }

    public BookmarkResponse setBlog(BlogResponse blog) {
        this.blog = blog;
        return this;
    }
}
