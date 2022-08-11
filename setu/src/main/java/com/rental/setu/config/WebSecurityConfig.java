package com.rental.setu.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class to set up interceptor
 *
 * @author jimil
 */
@Configuration
public class WebSecurityConfig implements WebMvcConfigurer {

    /**
     * Over-riden method to add custom interceptor
     * onto the list of interceptors stored with
     * the Interceptor Registry
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CustomRequestInterceptor());
    }

}
