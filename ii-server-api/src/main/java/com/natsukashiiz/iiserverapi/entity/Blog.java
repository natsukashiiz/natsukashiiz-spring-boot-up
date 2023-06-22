package com.natsukashiiz.iiserverapi.entity;

import com.natsukashiiz.iicommon.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "ii_blogs")
@Data
public class Blog extends BaseEntity {
    @Column(nullable = false)
    private String title;
    @Lob
    private String content;
    private boolean publish;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "uid")
    private User user;
}
