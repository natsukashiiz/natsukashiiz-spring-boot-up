package com.natsukashiiz.iiboot.configuration.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Authentication {
    private Long uid;
    private String name;
}
