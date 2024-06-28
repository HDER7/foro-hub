package com.example.forohub.infra.springdoc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                .info(new Info()
                        .title("API ForoHub")
                        .description("Foro-Hub es una API diseñada para gestionar un foro. Permite a los usuarios crear, leer, actualizar y eliminar publicaciones y comentarios, así como gestionar usuarios y temas del foro.")
                        .contact(new Contact()
                                .email("heiderarb@gmail.com"))
                        .license(new License()
                                .name("Apache 2.0")));  }

}
