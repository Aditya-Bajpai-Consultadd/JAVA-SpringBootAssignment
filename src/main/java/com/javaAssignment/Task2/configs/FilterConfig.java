package com.javaAssignment.Task2.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
        @Bean
        public JwtFilter jwtFilter() {
            return new JwtFilter();
        }
}
