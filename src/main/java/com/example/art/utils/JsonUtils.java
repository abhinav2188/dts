package com.example.art.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String convertObjectToJsonString(Object object) throws JsonProcessingException {
        if(object == null)
            return null;
        return objectMapper.writeValueAsString(object);
    }

    private static <T> T covertObjectFromJsonString(String json, Class<T> clazz){
        return objectMapper.convertValue(json,clazz);
    }

}
