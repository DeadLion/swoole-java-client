package com.chelun.soa.protocol.serializer;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.chelun.soa.util.JsonBinder;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonSerializer {
	private static final Logger  logger = LoggerFactory.getLogger(JsonSerializer.class);
	private JsonBinder jsonBinder = JsonBinder.getNonNullBinder();
	public final static ObjectMapper mapper = new ObjectMapper(); 
	
	public byte[] serialize(Object obj) throws Exception {
		String jsonStr = jsonBinder.toJson(obj);
		logger.info("JSONSerialize:serialize: " + jsonStr);
		return jsonStr.getBytes();
	}

	public Object deserialize(byte[] data, Class<?> cls) throws Exception {
		String jsonStr = new String(data).replace("\\\"", "\"");
		if(jsonStr.startsWith("\"")){
			jsonStr = jsonStr.substring(1, jsonStr.length() -1);
		}
		logger.info("JSONSerialize:deserialize: " + jsonStr);
		jsonStr = unescapeJava(jsonStr);
		Object obj = jsonBinder.fromJson(jsonStr, cls);
		
		return obj;
	}
	
	public Object deserialize(String str, Class<?> cls, Class<?> cls2) throws Exception {
		String jsonStr = str.replace("\\\"", "\"");
		if(jsonStr.startsWith("\"")){
			jsonStr = jsonStr.substring(1, jsonStr.length() -1);
		}
		logger.info("JSONSerialize:deserialize: " + jsonStr);
		jsonStr = unescapeJava(jsonStr);
		Object obj = jsonBinder.fromJson(jsonStr, getCollectionType(cls, cls2));
		
		return obj;
	}
	
	public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }
	
	public static String unicode2String(String unicode) {
	    StringBuffer string = new StringBuffer();
	    String[] hex = unicode.split("\\\\u");
	    for (int i = 1; i < hex.length; i++) {
	        int data = Integer.parseInt(hex[i], 16);
	        string.append((char) data);
	    }
	    return string.toString();
	}
	
	public static String string2Unicode(String string) {
	    StringBuffer unicode = new StringBuffer();
	    for (int i = 0; i < string.length(); i++) {
	        char c = string.charAt(i);
	        unicode.append("\\u" + Integer.toHexString(c));
	    }
	 
	    return unicode.toString();
	}
	
	public static String unescapeJava(String str) {
        if (str == null) {
            return null;
        }
        try {
            StringWriter writer = new StringWriter(str.length());
            unescapeJava(writer, str);
            return writer.toString();
        } catch (IOException ioe) {
            // this should never ever happen while writing to a StringWriter
//            throw new UnhandledException(ioe);
        }
        return str;
    }

    public static void unescapeJava(Writer out, String str) throws IOException {
        if (out == null) {
            throw new IllegalArgumentException("The Writer must not be null");
        }
        if (str == null) {
            return;
        }
        int sz = str.length();
        StringBuffer unicode = new StringBuffer(4);
        boolean hadSlash = false;
        boolean inUnicode = false;
        for (int i = 0; i < sz; i++) {
            char ch = str.charAt(i);
            if (inUnicode) {
                // if in unicode, then we're reading unicode
                // values in somehow
                unicode.append(ch);
                if (unicode.length() == 4) {
                    // unicode now contains the four hex digits
                    // which represents our unicode character
                    try {
                        int value = Integer.parseInt(unicode.toString(), 16);
                        out.write((char) value);
                        unicode.setLength(0);
                        inUnicode = false;
                        hadSlash = false;
                    } catch (NumberFormatException nfe) {
//                        throw new NestableRuntimeException("Unable to parse unicode value: " + unicode, nfe);
                    }
                }
                continue;
            }
            if (hadSlash) {
                // handle an escaped value
                hadSlash = false;
                switch (ch) {
                    case '\\':
                        out.write('\\');
                        break;
                    case '\'':
                        out.write('\'');
                        break;
                    case '\"':
                        out.write('"');
                        break;
                    case 'r':
                        out.write('\r');
                        break;
                    case 'f':
                        out.write('\f');
                        break;
                    case 't':
                        out.write('\t');
                        break;
                    case 'n':
                        out.write('\n');
                        break;
                    case 'b':
                        out.write('\b');
                        break;
                    case 'u': {
                        // uh-oh, we're in unicode country....
                        inUnicode = true;
                        break;
                    }
                    default:
                        out.write(ch);
                        break;
                }
                continue;
            } else if (ch == '\\') {
                hadSlash = true;
                continue;
            }
            out.write(ch);
        }
        if (hadSlash) {
            // then we're in the weird case of a \ at the end of the
            // string, let's output it anyway.
            out.write('\\');
        }
    }
}
