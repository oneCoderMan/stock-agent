package com.codersim.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.TimeZone;

/**
 * @Author： yijun
 * @DATE: 2023/9/16 17:17
 * @Description
 */
public class JsonUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .setTimeZone(TimeZone.getDefault());


    public static <T> T fromJson(String jsonContent, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(jsonContent, clazz);
        } catch (IOException e) {
            LOGGER.error("Fail to convert json{} to bean{}", jsonContent, clazz, e);
            throw new IllegalStateException("Fail to parse json str");
        }
    }

    /**
     * json字符串转换为Java泛型对象
     * @param jsonContent
     * @param typeReference
     * @return
     * @param <T>
     *    List<Person> people1 =
     *    JsonUtil.fromJson(s, new TypeReference<List<Person>>() {});
     */
    public static <T> T fromJson(String jsonContent, TypeReference<T> typeReference) {
        try {
            return OBJECT_MAPPER.readValue(jsonContent, typeReference);
        } catch (IOException e) {
            LOGGER.error("Fail to convert json:{} to bean:{}", jsonContent, typeReference, e);
            throw new IllegalStateException("Fail to parse json str");
        }
    }

    /**
     * 将Javabean转换为Json字符串
     * @param obj
     * @return
     */
    public static String toJson(Object obj) {
        if (obj == null) {
            return "";
        }
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            LOGGER.error("failed to process json obj", e);
            return "";
        }
    }

    /**
     * 将Json格式化，换行
     * @param obj
     * @return
     */
    public static String writePrettyValue(Object obj) {
        if (obj == null) {
            return "";
        }
        try {
            return OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            LOGGER.error("Failed to parse object to json str", e);
            return "";
        }
    }



}
