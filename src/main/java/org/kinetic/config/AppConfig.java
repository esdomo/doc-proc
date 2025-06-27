package org.kinetic.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("config")
@Getter
@Setter
public class AppConfig {
    private String inputFolder;
}
