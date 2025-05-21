package com.example.backend.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class EnvLogger {

    @Autowired
    private Environment env;

    @PostConstruct
    public void logDatasourceUrl() {
        System.out.println("🌐 Spring datasource.url = "
                + env.getProperty("spring.datasource.url"));
    }
}
