package com.chelun.soa.util;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.RoundingMode;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonNumberFormatVisitor;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;

public class CustomizedLongSerializer extends StdScalarSerializer<Long> {

    public CustomizedLongSerializer() {
        super(Long.class);
    }

    @Override
    public void serialize(Long value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonGenerationException {
        BigDecimal decimal = new BigDecimal(String.valueOf(value.floatValue())).setScale(6, RoundingMode.HALF_UP);
        jgen.writeNumber(decimal.toPlainString());
    }

    @Override
    public JsonNode getSchema(SerializerProvider provider, Type typeHint) {
        return createSchemaNode("number", true);
    }

    @Override
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
        JsonNumberFormatVisitor v2 = visitor.expectNumberFormat(typeHint);
        if (v2 != null) {
            v2.numberType(JsonParser.NumberType.LONG);
        }
    }

}
