package com.example.company.config;

import com.example.company.security.ApiKeyFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, ApiKeyFilter apiKeyFilter) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)

                .cors(cors -> cors.configurationSource(request -> {
                    var config = new org.springframework.web.cors.CorsConfiguration();

                    config.setAllowedOrigins(java.util.List.of(
                            "https://merakisystemstech.com",
                            "https://test.merakisystemstech.com"
                    ));

                    config.setAllowedMethods(java.util.List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    config.setAllowedHeaders(java.util.List.of("*"));
                    config.setAllowCredentials(true);

                    return config;
                }))

                .authorizeHttpRequests(auth -> auth
                        // PROTECTED
                        .requestMatchers(
                                "/v1/api/careers/actions/**",
                                "/v1/api/contact/ops/**",
                                "/v1/api/faqs/manage/**",
                                "/v1/api/partners/manage/**",
                                "/v1/api/policies/manage/**",
                                "/v1/api/projects/manage/**"
                        ).authenticated()

                        // PUBLIC
                        .requestMatchers(
                                "/", "/public/**",
                                "/favicon.ico",
                                "/swagger-ui.html", "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/api-docs/**",
                                "/swagger-resources/**",
                                "/webjars/**",
                                "/h2-console/**",

                                "/v1/api/careers", "/v1/api/careers/*",
                                "/v1/api/faqs/**",
                                "/v1/api/contact",
                                "/v1/api/policies/**",
                                "/v1/api/partners/**",
                                "/v1/api/projects/**",
                                "/api/subscription/**"
                        ).permitAll()

                        .anyRequest().denyAll()
                )

                .addFilterBefore(apiKeyFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable);

        http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));

        return http.build();
    }}