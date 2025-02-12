package com.redis.endpoint.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtil {

    public String getProperty(String property) {
        Properties properties = new Properties();
        InputStream inputStream =
                getClass().getClassLoader().getResourceAsStream("application-prod.properties");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties.getProperty(property);
    }
}
