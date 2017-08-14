package com.chelun.soa.util;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * 
 * @author danson.liu
 * @version $Id: CustomizedNullKeySerializer.java, v 0.1 2013-6-27 下午05:00:55 danson.liu Exp $
 */
public class CustomizedNullKeySerializer extends JsonSerializer<Object> {

    private static Logger logger = LoggerFactory.getLogger(CustomizedNullKeySerializer.class);

    /** 
     * @see com.fasterxml.jackson.databind.JsonSerializer#serialize(java.lang.Object, com.fasterxml.jackson.core.JsonGenerator, com.fasterxml.jackson.databind.SerializerProvider)
     */
    @Override
    public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        logger.warn("There is a map has a null-key element to be serialized by jackson.");
        jgen.writeFieldName("null");
    }

}
