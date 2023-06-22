package com.natsukashiiz.iiserverapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.natsukashiiz.iicommon.common.DeviceState;
import com.natsukashiiz.iicommon.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "ii_sign_history")
@Data
public class SignHistory extends BaseEntity {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "uid")
    private User user;
    @Column(nullable = false, length = 32)
    private String ipv4;
    @JsonIgnore
    @Column(nullable = false)
    private String ua;
    @Column(nullable = false)
    private Integer device = DeviceState.Unknown.getValue();
}
