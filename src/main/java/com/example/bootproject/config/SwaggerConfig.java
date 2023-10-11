package com.example.bootproject.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI(@Value("${springdoc.version}") String springdocVersion) {
        Info info = new Info()
                .title("부트 프로젝트 - 백엔드 문서")
                .version(springdocVersion)
                .description("부트 프로젝트의 백엔드 swagger 문서입니다");

        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}
