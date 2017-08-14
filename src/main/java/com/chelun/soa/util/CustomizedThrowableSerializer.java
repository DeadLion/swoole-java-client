package com.chelun.soa.util;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * 
 * @author danson.liu
 * @version $Id: CustomizedThrowableSerializer.java, v 0.1 2013-6-26 下午03:11:59 danson.liu Exp $
 */
public class CustomizedThrowableSerializer extends JsonSerializer<Throwable> {

    private static Logger                           logger              = LoggerFactory.getLogger(CustomizedThrowableSerializer.class);

    private static ConcurrentMap<Class<?>, Field[]> throwableFieldCache = new ConcurrentHashMap<Class<?>, Field[]>();

    /** 
     * @see com.fasterxml.jackson.databind.JsonSerializer#serialize(java.lang.Object, com.fasterxml.jackson.core.JsonGenerator, com.fasterxml.jackson.databind.SerializerProvider)
     */
    @Override
    public void serialize(Throwable throwable, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        if (throwable != null) {
            jgen.writeStartObject();
            Class<? extends Throwable> throwableClazz = throwable.getClass();
            jgen.writeStringField("type", throwableClazz.getName());
            Field[] fields = getThrowableFields(throwableClazz);
            for (Field field : fields) {
                String fieldName = field.getName();
                if (!"stackTrace".equals(fieldName)) {
                    field.setAccessible(true);
                    Object fieldVal;
                    try {
                        fieldVal = field.get(throwable);
                    } catch (Exception e) {
                        logger.warn("Get exception[" + throwableClazz.getName() + "]'s field[" + fieldName + "] value failed.", e);
                        fieldVal = null;
                    }
                    if (fieldVal != null && fieldVal != throwable) {
                        jgen.writeObjectField("detailMessage".equals(fieldName) ? "message" : fieldName, fieldVal);
                    }
                }
            }
            jgen.writeEndObject();
        }
    }

    private Field[] getThrowableFields(Class<? extends Throwable> throwableClazz) {
        Field[] fields = throwableFieldCache.get(throwableClazz);
        if (fields == null) {
            fields = ReflectionUtil.getNonstaticFields(throwableClazz);
            Field[] oldFields = throwableFieldCache.putIfAbsent(throwableClazz, fields);
            if (oldFields != null) {
                fields = oldFields;
            }
        }
        return fields;
    }

    /** 
     * @see com.fasterxml.jackson.databind.JsonSerializer#handledType()
     */
    @Override
    public Class<Throwable> handledType() {
        return Throwable.class;
    }

}
