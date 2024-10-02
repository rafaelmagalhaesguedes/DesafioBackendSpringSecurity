package com.rental.service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig implements OpenApiCustomizer {

    @Override
    public void customise(OpenAPI openApi) {
        Info info = new Info()
                .title("Desafio Backend Spring Security + JWT")
                .description("O desafio consiste em criar um microsserviço de autenticação e autorização de usuários em um sistema, integrando o Spring Security com JWT para gerenciamento de tokens. Foram implementados mecanismos de criptografia para proteger dados sensíveis e utilizada herança entre as classes para organizar e compartilhar comportamentos entre os diferentes tipos de usuários: Admin, Manager e Customer, cada um com permissões distintas.")
                .version("1.0.0");

        openApi.info(info);
    }
}
