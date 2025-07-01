package org.kinetic.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI documentProcessorApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Document Processor API")
                        .description("API documentation for the Document Processor application")
                        .version("1.0"));
    }
}
