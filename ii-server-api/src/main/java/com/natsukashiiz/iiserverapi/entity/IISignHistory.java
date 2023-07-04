package com.natsukashiiz.iiserverapi.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

public class IISignHistory implements Serializable {
    private Long id;
    private Integer device;
    private String ipv4;
    private String ua;
    private Long uid;
    private Integer version;
    private LocalDateTime cdt;
    private LocalDateTime udt;

    public Long getId() {
        return id;
    }

    public IISignHistory setId(Long id) {
        this.id = id;
        return this;
    }

    public Integer getDevice() {
        return device;
    }

    public IISignHistory setDevice(Integer device) {
        this.device = device;
        return this;
    }

    public String getIpv4() {
        return ipv4;
    }

    public IISignHistory setIpv4(String ipv4) {
        this.ipv4 = ipv4;
        return this;
    }

    public String getUa() {
        return ua;
    }

    public IISignHistory setUa(String ua) {
        this.ua = ua;
        return this;
    }

    public Long getUid() {
        return uid;
    }

    public IISignHistory setUid(Long uid) {
        this.uid = uid;
        return this;
    }

    public Integer getVersion() {
        return version;
    }

    public IISignHistory setVersion(Integer version) {
        this.version = version;
        return this;
    }

    public LocalDateTime getCdt() {
        return cdt;
    }

    public IISignHistory setCdt(LocalDateTime cdt) {
        this.cdt = cdt;
        return this;
    }

    public LocalDateTime getUdt() {
        return udt;
    }

    public IISignHistory setUdt(LocalDateTime udt) {
        this.udt = udt;
        return this;
    }
}
