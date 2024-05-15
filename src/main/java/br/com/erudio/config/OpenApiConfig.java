package br.com.erudio.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    OpenAPI customOpenAPI() {
       return new OpenAPI()
                .info(
                        new Info()
                            .title("API RESTFull Java 17 and Spring Boot 3")
                            .version("v1")
                            .description("RESTFull API developed in-house and best practices")
                            .license(
                                    new License()
                                        .url("http://pub.api.backend.com")
                                        .name("Apache 2"))
                                .termsOfService("http://pub.api.backend.com")
                            .contact(new Contact()
                                        .email("vinikjhgfds@gmail.com"))
                );
    }
}
