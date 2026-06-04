package com.example.company;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(
        exclude = {UserDetailsServiceAutoConfiguration.class}
)
@ConfigurationPropertiesScan
public class CompanyWebsiteApplication {
    public static void main(String[] args) {
        SpringApplication.run(CompanyWebsiteApplication.class, args);
    }
}