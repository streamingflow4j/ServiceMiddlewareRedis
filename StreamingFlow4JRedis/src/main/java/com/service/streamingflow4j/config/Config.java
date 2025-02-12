package com.service.streamingflow4j.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
public class Config {

    @Configuration
    @Profile("default")
    @PropertySource("classpath:application.properties")
    static class DefaultProperties {
    }

    @Configuration
    @Profile("!default")
    @PropertySource({"classpath:application.properties", "classpath:application-${spring.profiles.active}.properties"})
    static class NonDefaultProperties {
    }
}
