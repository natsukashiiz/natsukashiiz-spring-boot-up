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
@Entity(name = "ii_categories")
@Data
public class Category extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @JsonIgnore
    @Fetch(value = FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "category")
    private Set<Blog> blogs;
}
