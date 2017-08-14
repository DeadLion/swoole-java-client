package com.chelun.soa.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.NullNode;

/**
 * 
 * @author danson.liu
 * @version $Id: JsonBinder.java, v 0.1 2013-6-24 下午03:52:43 danson.liu Exp $
 */
public class JsonBinder {

    private static class NonNullSingletonHolder {
        public static final JsonBinder nonNullBinder = new JsonBinder(Include.NON_NULL);
        public static final JsonBinder alwaysBinder  = new JsonBinder(Include.ALWAYS);
    }

    public static JsonBinder getNonNullBinder() {
        return NonNullSingletonHolder.nonNullBinder;
    }

    public static JsonBinder getAlwaysBinder() {
        return NonNullSingletonHolder.alwaysBinder;
    }

    private ObjectMapper mapper;

    private JsonBinder(Include include) {
        mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        mapper.setSerializationInclusion(include);
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.enable(SerializationFeature.WRITE_CHAR_ARRAYS_AS_JSON_ARRAYS);
        mapper.enable(SerializationFeature.WRITE_BIGDECIMAL_AS_PLAIN);
        mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        mapper.getSerializerProvider().setNullKeySerializer(new CustomizedNullKeySerializer());
        SimpleModule pegasusModule = new SimpleModule("PegasusModule", Version.unknownVersion());
        pegasusModule.addSerializer(new CustomizedThrowableSerializer());
        pegasusModule.addSerializer(new CustomizedDecimalSerializer());
        pegasusModule.addSerializer(Long.class, new CustomizedLongSerializer());
        pegasusModule.addSerializer(Long.TYPE, new CustomizedLongSerializer());
        pegasusModule.addSerializer(Float.class, new CustomizedFloatSerializer());
        pegasusModule.addSerializer(Float.TYPE, new CustomizedFloatSerializer());
        pegasusModule.addSerializer(Double.class, new CustomizedDoubleSerializer());
        pegasusModule.addSerializer(Double.TYPE, new CustomizedDoubleSerializer());
        mapper.registerModule(pegasusModule);
    }

    public String toJson(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (IOException e) {
            throw new JsonSerializedException("Serialized Object to json string error : " + object, e);
        }
    }

    public <T> T fromJson(String json, Class<T> clazz) {
        if (json == null || "".equals(json.trim())) {
            return null;
        }
        try {
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new JsonDeserializedException("Deserialized json string error : " + json, e);
        }
    }

    public <T> T fromJson(String json, JavaType javaType) {
        if (json == null || "".equals(json.trim())) {
            return null;
        }
        try {
            return mapper.readValue(json, javaType);
        } catch (IOException e) {
            throw new JsonDeserializedException("Deserialized json string error : " + json, e);
        }
    }

    public Map<String, String> toMap(String json) {
        if (json == null || "".equals(json.trim())) {
            return null;
        }
        try {
            JsonNode tree = mapper.readTree(json);
            Map<String, String> map = new HashMap<String, String>(tree.size());
            Iterator<Entry<String, JsonNode>> fieldIter = tree.fields();
            while (fieldIter.hasNext()) {
                Entry<String, JsonNode> next = fieldIter.next();
                if (next.getValue() instanceof NullNode) {
                    map.put(next.getKey(), null);
                } else {
                    map.put(next.getKey(), next.getValue().toString());
                }
            }
            return map;
        } catch (Exception e) {
            throw new JsonDeserializedException("Deserialized json string to map error : " + json, e);
        }
    }

}
