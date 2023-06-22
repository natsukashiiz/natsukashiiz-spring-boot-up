package com.natsukashiiz.iiserverapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.natsukashiiz.*"})
@EntityScan(basePackages = {"com.natsukashiiz.*"})
@EnableJpaRepositories(basePackages = {"com.natsukashiiz.*"})
public class IIServerApiApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(IIServerApiApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(IIServerApiApplication.class);
    }


}
