package com.natsukashiiz.iiserverapi.model.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BlogResponse {
    private Long id;
    private String title;
    private String content;
    private String category;
    private Boolean publish;
    private LocalDateTime cdt;
    private Long uid;
    private String uname;
    private Boolean bookmark;

    public Long getId() {
        return id;
    }

    public BlogResponse setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public BlogResponse setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public BlogResponse setContent(String content) {
        this.content = content;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public BlogResponse setCategory(String category) {
        this.category = category;
        return this;
    }

    public Boolean getPublish() {
        return publish;
    }

    public BlogResponse setPublish(Boolean publish) {
        this.publish = publish;
        return this;
    }

    public LocalDateTime getCdt() {
        return cdt;
    }

    public BlogResponse setCdt(LocalDateTime cdt) {
        this.cdt = cdt;
        return this;
    }

    public Long getUid() {
        return uid;
    }

    public BlogResponse setUid(Long uid) {
        this.uid = uid;
        return this;
    }

    public String getUname() {
        return uname;
    }

    public BlogResponse setUname(String uname) {
        this.uname = uname;
        return this;
    }

    public Boolean getBookmark() {
        return bookmark;
    }

    public BlogResponse setBookmark(Boolean bookmark) {
        this.bookmark = bookmark;
        return this;
    }
}
