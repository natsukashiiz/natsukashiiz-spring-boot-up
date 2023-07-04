package com.natsukashiiz.iiserverapi.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

public class IIBookmark implements Serializable {
    private Long id;
    private Long blogId;
    private Long uid;
    private Integer version;
    private LocalDateTime cdt;
    private LocalDateTime udt;

    public Long getId() {
        return id;
    }

    public IIBookmark setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getBlogId() {
        return blogId;
    }

    public IIBookmark setBlogId(Long blogId) {
        this.blogId = blogId;
        return this;
    }

    public Long getUid() {
        return uid;
    }

    public IIBookmark setUid(Long uid) {
        this.uid = uid;
        return this;
    }

    public Integer getVersion() {
        return version;
    }

    public IIBookmark setVersion(Integer version) {
        this.version = version;
        return this;
    }

    public LocalDateTime getCdt() {
        return cdt;
    }

    public IIBookmark setCdt(LocalDateTime cdt) {
        this.cdt = cdt;
        return this;
    }

    public LocalDateTime getUdt() {
        return udt;
    }

    public IIBookmark setUdt(LocalDateTime udt) {
        this.udt = udt;
        return this;
    }
}
