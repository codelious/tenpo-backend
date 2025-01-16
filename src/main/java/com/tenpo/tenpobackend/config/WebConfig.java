package com.tenpo.tenpobackend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final RateLimitingInterceptor rateLimitingInterceptor;

    @Autowired
    public WebConfig(RateLimitingInterceptor rateLimitingInterceptor) {
        this.rateLimitingInterceptor = rateLimitingInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Register the RateLimitingInterceptor to apply rate limiting to incoming requests
        registry.addInterceptor(rateLimitingInterceptor)
                .excludePathPatterns(
                        "/swagger-ui.html",  // Exclude the Swagger UI
                        "/swagger-ui/**",    // Exclude Swagger UI resources
                        "/v3/api-docs/**",   // Exclude OpenAPI/Swagger docs
                        "/webjars/**"        // Exclude Swagger's static resources
                );
    }
}
