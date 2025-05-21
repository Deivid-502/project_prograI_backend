package com.example.backend.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Al arrancar, imprime la URL de la base de datos en consola.
 * Sirve para verificar en qué entorno estamos conectados.
 */
@Configuration
public class EnvLogger {

    @Autowired
    private Environment env;

    @PostConstruct
    public void logDatasourceUrl() {
        String url = env.getProperty("spring.datasource.url");
        System.out.println("[*] Conectando a DB: " + url);
    }
}
