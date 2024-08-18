package com.xuecheng.base.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Value("${swagger.title}")
    private String title;
    @Value("${swagger.description}")
    private String description;
    @Value("${swagger.version}")
    private String version;

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title(title)
                .description(description)
                .version(version);
        return new OpenAPI()
                .info(info);
    }
}
