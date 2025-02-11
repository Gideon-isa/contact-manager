package com.gideon.contact_manager.config;

import com.gideon.contact_manager.application.usecase.user.PasswordServiceImpl;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@Configuration
public class ServiceConfig {
    @Value("${app.password-source.salt}")
    private String salt;

    @Value("${app.password-source.pepper}")
    private String pepper;

    @Value("${app.password-source.token-signing-key}")
    private String signingKey;

    @Value("${app.password-source.token-expiration-minutes}")
    private int expirationDuration;

    @Value("${app.password-source.number-of-iteration}")
    private int numberOfIterations;

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info()
                .title("Phone Book API")
                .description("An API that manage contacts")
                .version("v1.0"));
    }

    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> Optional.of("system");
    }

    @Bean
    public PasswordServiceImpl passwordService() {
        return new PasswordServiceImpl(salt, pepper, signingKey, expirationDuration, numberOfIterations);
    }
}
