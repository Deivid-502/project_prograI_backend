package com.example.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuración CORS usando la variable de entorno FRONTEND_URL.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    // Injecta el valor de la variable de entorno FRONTEND_URL
    @Value("${FRONTEND_URL}")
    private String frontendUrl;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins(frontendUrl)  // se toma dinámicamente
                .allowedMethods("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
