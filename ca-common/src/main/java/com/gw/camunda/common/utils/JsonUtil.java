package com.gw.camunda.common.utils;

/**
 * @author guanwu
 * @created on 2024-02-04 17:10:57
 **/

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class JsonUtil {

    private JsonUtil() {

    }

    private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);
    private static ObjectMapper objectMapper = new ObjectMapper();
    static {
        //静默出现未知属性时的异常
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        //允许json=""的空字符换入参
        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
    }

    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage() + " :" + object.getClass().getName());
        }
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return (T)objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("json is not valid. " + json);
        }
    }

    public static <T> T fromJson(String json, TypeReference<T> type){
        try {
            return (T)objectMapper.readValue(json, type);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("json is not valid. " + json);
        }
    }
}
