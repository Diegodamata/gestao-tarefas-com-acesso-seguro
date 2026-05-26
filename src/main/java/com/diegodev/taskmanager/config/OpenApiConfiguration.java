package com.diegodev.taskmanager.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API Gestao de Taferas com Acesso Seguro",
                version = "v1",
                contact = @Contact(
                        name = "Diego da Mata",
                        email = "diegodamatapontes@hotmail.com"
                )
        )
)
public class OpenApiConfiguration {
}
