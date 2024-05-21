package com.natsukashiiz.iiserverapi.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

public class IIBlog implements Serializable {
    private Long id;
    private String title;
    private String content;
    private String category;
    private Long categoryId;
    private Long uid;
    private String uname;
    private String avatar;
    private boolean publish;
    private boolean bookmark;
    private Integer version;
    private LocalDateTime cdt;
    private LocalDateTime udt;

    public IIBlog() {
    }

    public Long getId() {
        return id;
    }

    public IIBlog setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public IIBlog setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public IIBlog setContent(String content) {
        this.content = content;
        return this;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public IIBlog setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public Long getUid() {
        return uid;
    }

    public IIBlog setUid(Long uid) {
        this.uid = uid;
        return this;
    }

    public Integer getVersion() {
        return version;
    }

    public IIBlog setVersion(int version) {
        this.version = version;
        return this;
    }

    public LocalDateTime getCdt() {
        return cdt;
    }

    public IIBlog setCdt(LocalDateTime cdt) {
        this.cdt = cdt;
        return this;
    }

    public LocalDateTime getUdt() {
        return udt;
    }

    public IIBlog setUdt(LocalDateTime udt) {
        this.udt = udt;
        return this;
    }

    public boolean isPublish() {
        return publish;
    }

    public IIBlog setPublish(boolean publish) {
        this.publish = publish;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public IIBlog setCategory(String category) {
        this.category = category;
        return this;
    }

    public String getUname() {
        return uname;
    }

    public IIBlog setUname(String uname) {
        this.uname = uname;
        return this;
    }

    public boolean isBookmark() {
        return bookmark;
    }

    public IIBlog setBookmark(boolean bookmark) {
        this.bookmark = bookmark;
        return this;
    }

    public String getAvatar() {
        return avatar;
    }

    public IIBlog setAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }
}
