package com.example.demowebclient.utils.json;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Component
public class JsonPathPropertiesHelper {
    @Value("${jsonpath.properties.location}")
    private String jsonPathPropertiesLocation;
    private final ResourceLoader resourceLoader;
    private static final Logger log = LoggerFactory.getLogger(JsonPathPropertiesHelper.class);

    public JsonPathPropertiesHelper(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public Map<String, String> getAllProperties()  {

        Map<String, String> propertyMap = new TreeMap<>();
        Resource resource = resourceLoader.getResource(jsonPathPropertiesLocation);

        try (InputStream inputStream = resource.getInputStream()) {
            Properties properties = new Properties();
            properties.load(inputStream);

            for (String key : properties.stringPropertyNames()) {
                String value = properties.getProperty(key);
                propertyMap.put(key, value);
            }
        } catch (IOException e) {
            log.error("Properties File Not Found");
        }

        return propertyMap;
    }
}
