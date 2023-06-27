package com.natsukashiiz.iiserverapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.natsukashiiz.iicommon.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "ii_blogs")
@Data
public class Blog extends BaseEntity {
    @Column(nullable = false)
    private String title;
    @Lob
    private String content;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;
    private boolean publish;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "uid")
    private User user;
    @JsonIgnore
    @Fetch(value = FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "blog", fetch = FetchType.EAGER)
    private Set<Bookmark> bookmarks;
}
