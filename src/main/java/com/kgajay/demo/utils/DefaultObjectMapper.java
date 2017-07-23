package com.kgajay.demo.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

/**
 * @author ajay.kg created on 23/07/17.
 */
public enum DefaultObjectMapper {

    INSTANCE;

    private ObjectMapper objectMapper;

    private DefaultObjectMapper() {
        objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
