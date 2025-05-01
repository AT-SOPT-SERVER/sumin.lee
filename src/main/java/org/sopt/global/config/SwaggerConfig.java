package org.sopt.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

        @Bean
        public OpenAPI openAPI() {
                return new OpenAPI()
                        .addServersItem(new Server().url("http://localhost:8080").description("Local Development Server")) // HTTPS 서버 추가
                        .info(apiInfo());
        }

        private Info apiInfo() {
                return new Info()
                        .title("Spoony API Docs")
                        .description("API documentation for AWS Study using Swagger UI.")
                        .version("1.0.0");
        }
}
