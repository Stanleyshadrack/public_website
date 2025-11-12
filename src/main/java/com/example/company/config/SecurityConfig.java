package com.example.company.config;

import com.example.company.security.ApiKeyFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, ApiKeyFilter apiKeyFilter) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                        // 🔒 Internal routes (protected by ApiKeyFilter)
                        .requestMatchers(
                                "/v1/api/careers/actions/**",
                                "/v1/api/contact/ops/**",
                                "/v1/api/faqs/manage/**",
                                "/v1/api/partners/manage/**",
                                "/v1/api/policies/manage/**",
                                "/v1/api/projects/manage/**"
                        ).permitAll()


                        // Public routes (accessible without API key)
                        .requestMatchers(
                                "/", "/public/**",
                                "/favicon.ico",
                                "/swagger-ui.html", "/swagger-ui/**",
                                "/api-docs/**",
                                "/v1/api/careers", "/v1/api/careers/*",
                                "/v1/api/faqs/**",
                                "/v1/api/contact",
                                "/v1/api/policies/**",
                                "/v1/api/partners/**",
                                "/v1/api/projects/**",
                                "/api/subscription/**",
                                "/h2-console/**"
                        ).permitAll()

                        // Deny everything else by default
                        .anyRequest().denyAll()
                )
                // Add ApiKeyFilter to secure internal routes
                .addFilterBefore(apiKeyFilter, UsernamePasswordAuthenticationFilter.class)
                // Disable form and HTTP Basic
                .httpBasic(basic -> basic.disable())
                .formLogin(form -> form.disable());

        // 🧩 Allow frames (for H2 console)
        http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));

        return http.build();
    }
}
