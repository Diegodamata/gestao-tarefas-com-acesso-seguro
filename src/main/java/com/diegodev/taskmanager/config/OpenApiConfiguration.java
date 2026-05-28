package com.diegodev.taskmanager.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
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
        ),
        security = { //adicionando o botao de authentication na doc, exige que os endpoints tenha autenticacao, ira procurar um schema chamado bearerToken
                @SecurityRequirement(name = "bearerToken")
        }
)
@SecurityScheme( //criei um esquema de seguranca
        name = "bearerToken", //referencia para o botao, precisa ser o mesmo nome
        type = SecuritySchemeType.HTTP, //como o token sera enviado, sera baseado no protocolo http
        bearerFormat = "JWT", //formato do token
        scheme = "bearer", //tipo de authorization header ex: Authorization bearer token
        in = SecuritySchemeIn.HEADER //informando que o token sera enviado pelo header ex: Authorization bearer token
)
public class OpenApiConfiguration {
}
