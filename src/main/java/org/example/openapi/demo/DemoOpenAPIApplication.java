package org.example.openapi.demo;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableJdbcRepositories(considerNestedRepositories = true)
@SpringBootApplication
public class DemoOpenAPIApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoOpenAPIApplication.class, args);
    }

    @SneakyThrows
    @Bean
    SecurityFilterChain security(HttpSecurity http) {
        http.authorizeHttpRequests().anyRequest().authenticated();
        http.csrf().disable();
        http.formLogin();
        http.httpBasic();
        return http.build();
    }

    @Autowired
    void removePageExtraFields(ObjectMapper objectMapper) {
        objectMapper.addMixIn(Page.class, PageMixin.class);
    }

    @JsonPropertyOrder({"totalElements", "totalPages", "first", "last", "content"})
    @JsonIncludeProperties({"totalElements", "totalPages", "first", "last", "content"})
    static class PageMixin {
    }

}
