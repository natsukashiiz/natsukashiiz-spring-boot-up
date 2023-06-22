package com.natsukashiiz.iiboot;

import com.natsukashiiz.iiboot.configuration.jwt.JwtAutoConfiguration;
import com.natsukashiiz.iiboot.configuration.jwt.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
public class IIBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(IIBootApplication.class, args);
    }
}
