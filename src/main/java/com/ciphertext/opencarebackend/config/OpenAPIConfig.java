package com.ciphertext.opencarebackend.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        type = SecuritySchemeType.HTTP,
        paramName = "Authorization",
        in = SecuritySchemeIn.HEADER,
        name = "bearerAuth",
        bearerFormat = "JWT",
        scheme = "bearer"
)
@OpenAPIDefinition(info = @Info(title = "Open Care API", version = "v1", description = "Open Care API Description"))
public class OpenAPIConfig {
}
