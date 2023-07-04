package com.natsukashiiz.iiserverapi.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

public class IICategory implements Serializable {
    private Long id;
    private String name;
    private Integer version;
    private LocalDateTime cdt;
    private LocalDateTime udt;

    public Long getId() {
        return id;
    }

    public IICategory setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public IICategory setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getVersion() {
        return version;
    }

    public IICategory setVersion(Integer version) {
        this.version = version;
        return this;
    }

    public LocalDateTime getCdt() {
        return cdt;
    }

    public IICategory setCdt(LocalDateTime cdt) {
        this.cdt = cdt;
        return this;
    }

    public LocalDateTime getUdt() {
        return udt;
    }

    public IICategory setUdt(LocalDateTime udt) {
        this.udt = udt;
        return this;
    }
}
