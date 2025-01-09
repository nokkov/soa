package ru.itmo.soa3.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Разрешить все пути
                .allowedOrigins("*") // Разрешить все домены
                .allowedMethods("GET", "POST", "PATCH", "DELETE", "OPTIONS", "HEAD") // Разрешить указанные методы
                .allowedHeaders("origin", "content-type", "accept", "authorization") // Разрешить указанные заголовки
                .allowCredentials(true) // Разрешить использование учётных данных
                .maxAge(1209600); // Максимальное время кэширования в секундах
    }
}
