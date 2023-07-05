package com.natsukashiiz.iiserverapi.model.response;

public class BookmarkResponse {
    private Long id;
    private BlogResponse blogs;

    public Long getId() {
        return id;
    }

    public BookmarkResponse setId(Long id) {
        this.id = id;
        return this;
    }

    public BlogResponse getBlogs() {
        return blogs;
    }

    public BookmarkResponse setBlogs(BlogResponse blogs) {
        this.blogs = blogs;
        return this;
    }
}
