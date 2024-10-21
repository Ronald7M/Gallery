package com.example.BackendShop.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final ProtectedInterceptor customAnnotationInterceptor;

    public WebConfig(ProtectedInterceptor customAnnotationInterceptor) {
        this.customAnnotationInterceptor = customAnnotationInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(customAnnotationInterceptor);
    }
}