package com.natsukashiiz.iiboot.configuration.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Data
@ConfigurationProperties(prefix = JwtProperties.PREFIX)
public class JwtProperties {
    public static final String PREFIX = "ii.jwt";
    private String issuer;
    private Long expirationMs;
    private RSAPublicKey publicKey;
    private RSAPrivateKey privateKey;

}
