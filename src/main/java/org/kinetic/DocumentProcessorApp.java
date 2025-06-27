package org.kinetic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class DocumentProcessorApp {

    public static void main(String[] args) {
        SpringApplication.run(DocumentProcessorApp.class, args);
    }
}
