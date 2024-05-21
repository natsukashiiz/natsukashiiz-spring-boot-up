package com.natsukashiiz.iiserverapi.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

public class IIUser implements Serializable {
    private Long id;
    private String email;
    private String username;
    private String password;
    private String avatar;
    private Integer state;
    private Integer version;
    private LocalDateTime cdt;
    private LocalDateTime udt;

    public Long getId() {
        return id;
    }

    public IIUser setId(Long id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public IIUser setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public IIUser setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public IIUser setPassword(String password) {
        this.password = password;
        return this;
    }

    public Integer getState() {
        return state;
    }

    public IIUser setState(Integer state) {
        this.state = state;
        return this;
    }

    public Integer getVersion() {
        return version;
    }

    public IIUser setVersion(Integer version) {
        this.version = version;
        return this;
    }

    public LocalDateTime getCdt() {
        return cdt;
    }

    public IIUser setCdt(LocalDateTime cdt) {
        this.cdt = cdt;
        return this;
    }

    public LocalDateTime getUdt() {
        return udt;
    }

    public IIUser setUdt(LocalDateTime udt) {
        this.udt = udt;
        return this;
    }

    public String getAvatar() {
        return avatar;
    }

    public IIUser setAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }
}
