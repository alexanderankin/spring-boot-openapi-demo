package org.example.openapi.demo;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Configuration
public class OpenAPIConfig {
    @Bean
    OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components()
                        .securitySchemes(
                                Map.of("basicAuth",
                                        Stream.of(new SecurityScheme())
                                                .peek(s -> s.setType(SecurityScheme.Type.HTTP))
                                                .peek(s -> s.setScheme("basic"))
                                                .findAny().orElseThrow()
                                )))
                .security(List.of(new SecurityRequirement().addList("basicAuth")))
                ;
    }
}
