package com.akash.github.redisexample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Configuration
public class YamlConfig {

    private final Yaml yaml = new Yaml();

    @Bean("configurationProperties")
    public ConfigurationProperties configurationProperties() throws FileNotFoundException {
        File file = new File(getClass().getClassLoader().getResource("application-config.yaml").getFile());
        FileInputStream inputStream = new FileInputStream(file);
        ConfigurationProperties props = yaml.loadAs(inputStream, ConfigurationProperties.class);
        return props;
    }
}
