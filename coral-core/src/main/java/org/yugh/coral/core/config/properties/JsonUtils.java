package org.yugh.coral.core.config.properties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Map;


@Slf4j
public final class JsonUtils {

    public static final ObjectMapper MAPPER = new ObjectMapper();
    public static final ObjectMapper CAMEL_MAPPER = new ObjectMapper();

    public static final ObjectMapper UNICODE_MAPPER = new ObjectMapper();

    private JsonUtils() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    static {
        MAPPER.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        CAMEL_MAPPER.setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE);
        CAMEL_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        UNICODE_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        SimpleModule module = new SimpleModule();
        UNICODE_MAPPER.registerModule(module);
        UNICODE_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static <T> T deserializeFromJson(String jsonString, Class<T> clazz) throws IOException {
        if (jsonString == null) {
            return null;
        }
        return MAPPER.readValue(jsonString, clazz);
    }

    public static <T> T deserializeFromJson(String jsonString, Class<T> clazz, boolean enableCamelCase) throws IOException {
        if (jsonString == null) {
            return null;
        }
        ObjectMapper mapper = MAPPER;
        if (enableCamelCase) {
            mapper = CAMEL_MAPPER;
        }
        return mapper.readValue(jsonString, clazz);
    }

    public static <T> T deserializeFromJson(String jsonString, TypeReference<T> typeReference) throws IOException {
        return deserializeFromJson(jsonString, typeReference, false);
    }

    public static <T> T deserializeFromJson(String jsonString, TypeReference<T> typeReference, boolean enableCamelCase) throws IOException {
        ObjectMapper mapper = MAPPER;
        if (enableCamelCase) {
            mapper = CAMEL_MAPPER;
        }
        if (jsonString == null) {
            return null;
        }
        return mapper.readValue(jsonString, typeReference);
    }

    public static JsonNode deserializeFromJson(String jsonString) throws IOException {
        if (jsonString == null) {
            return null;
        }
        return MAPPER.readTree(jsonString);
    }

    public static <T> T convertValue(Object fromValue, Class<T> toValueType) {
        return MAPPER.convertValue(fromValue, toValueType);
    }

    public static <T> T convertValue(Object fromValue, TypeReference<T> typeReference) {
        return MAPPER.convertValue(fromValue, typeReference);
    }

    public static String serializeToJson(Object object) {
        return serializeToJson(object, false);
    }

    public static String serializeToJson(Object object, boolean enableCamelCase) {
        ObjectMapper mapper = MAPPER;
        if (enableCamelCase) {
            mapper = CAMEL_MAPPER;
        }
        try {

            return mapper.writeValueAsString(object);
        } catch (IOException e) {
            log.info("catch exception when serializeToJson, object:{}, e:{}", object, e);
            throw new IllegalArgumentException("Can't serialize object to json.");
        }
    }

    public static JsonNode serializeToJsonNode(Object object) {
        try {
            return MAPPER.readTree(serializeToJson(object));
        } catch (IOException e) {
            log.info("catch exception when serializeToJsonNode, object:{}, e:{}", object, e);
            throw new IllegalArgumentException("Can't serialize object to json node.");
        }
    }

    public static JsonNode serializeToJsonNode(Object object, boolean enableCamelCase) {
        try {
            return MAPPER.readTree(serializeToJson(object, enableCamelCase));
        } catch (IOException e) {
            log.info("catch exception when serializeToJsonNode, object:{}, enableCamelCase {}, e:{}", object, enableCamelCase, e);
            throw new IllegalArgumentException("Can't serialize object to json node.");
        }
    }

    public static ObjectNode createObjectNode() {
        return MAPPER.createObjectNode();
    }

    public static <K, V> ObjectNode createObjectNode(Map<K, V> objectMap) {
        ObjectNode objectNode = MAPPER.createObjectNode();
        objectMap.forEach((k, v) -> objectNode.set(String.valueOf(k), serializeToJsonNode(v)));
        return objectNode;
    }

    public static <T> T deserializeFromJsonWithCamelCase(String jsonString, Class<T> clazz) throws IOException {
        if (jsonString == null) {
            return null;
        }
        return CAMEL_MAPPER.readValue(jsonString, clazz);
    }
}
