package com.natsukashiiz.iiserverapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.natsukashiiz.iicommon.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "ii_bookmarks")
@Data
public class Bookmark extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "blog_id")
    private Blog blog;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "uid")
    private User user;
}
