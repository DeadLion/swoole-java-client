package com.chelun.soa.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.util.TokenBuffer;

/**
 * 
 * @author danson.liu
 * @version $Id: CustomizedDecimalSerializer.java, v 0.1 2013-6-27 下午03:31:50 danson.liu Exp $
 */
public class CustomizedDecimalSerializer extends JsonSerializer<BigDecimal> {

    /** 
     * @see com.fasterxml.jackson.databind.JsonSerializer#serialize(java.lang.Object, com.fasterxml.jackson.core.JsonGenerator, com.fasterxml.jackson.databind.SerializerProvider)
     */
    @Override
    public void serialize(BigDecimal value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        if (value != null) {
            BigDecimal scaled = value.setScale(value.scale() > 2 ? 6 : 2, RoundingMode.HALF_UP);
            if (!(jgen instanceof TokenBuffer)) {
                jgen.writeNumber(scaled.toPlainString());
            } else {
                jgen.writeNumber(scaled);
            }
        }
    }

    /** 
     * @see com.fasterxml.jackson.databind.JsonSerializer#handledType()
     */
    @Override
    public Class<BigDecimal> handledType() {
        return BigDecimal.class;
    }

}
