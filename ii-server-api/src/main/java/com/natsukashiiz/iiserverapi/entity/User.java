package com.natsukashiiz.iiserverapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.natsukashiiz.iicommon.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "ii_users")
@Data
public class User extends BaseEntity {
    @Column(unique = true, nullable = false, length = 30, updatable = false)
    private String username;
    @Column(unique = true, nullable = false)
    private String email;
    @JsonIgnore
    @Column(nullable = false)
    private String password;
    @JsonIgnore
    @Fetch(value = FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "user")
    private Set<Blog> blogs;
    @JsonIgnore
    @Fetch(value = FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "user")
    private Set<SignHistory> signHistory;
}
