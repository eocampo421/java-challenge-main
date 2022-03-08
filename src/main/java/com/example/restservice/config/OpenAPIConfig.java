package com.example.restservice.config;

import static com.example.restservice.constants.SpecsConstants.API_INFO_CONTACT_EMAIL;
import static com.example.restservice.constants.SpecsConstants.API_INFO_DESCRIPTION;
import static com.example.restservice.constants.SpecsConstants.API_INFO_TITLE;
import static com.example.restservice.constants.SpecsConstants.API_INFO_VERSION_V1;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        final Info info = new Info()
            .title(API_INFO_TITLE)
            .description(API_INFO_DESCRIPTION)
            .version(API_INFO_VERSION_V1)
            .contact(new Contact().email(API_INFO_CONTACT_EMAIL));

        final Components components = new Components();
        return new OpenAPI()
            .components(components)
            .info(info);
    }
}