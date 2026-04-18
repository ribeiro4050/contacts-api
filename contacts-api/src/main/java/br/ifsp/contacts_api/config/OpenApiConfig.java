package br.ifsp.contacts_api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("Contacts API")
                        .version("1.0")
                        .description("API REST para gerenciamento de contatos e endereços desenvolvida na disciplina de API e Microsserviços.")
                        .contact(new Contact()
                                .name("Gustavo Ribeiro")
                                .email("gustavo@ifsp.edu.br")));

    }
}
