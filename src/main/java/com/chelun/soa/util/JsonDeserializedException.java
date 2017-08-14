package com.chelun.soa.util;

/**
 * 
 * @author danson.liu
 * @version $Id: JsonDeserializedException.java, v 0.1 2013-6-24 下午03:55:23 danson.liu Exp $
 */
public class JsonDeserializedException extends RuntimeException {

    private static final long serialVersionUID = 5626309708485053219L;

    public JsonDeserializedException(String message) {
        super(message);
    }

    public JsonDeserializedException(Throwable cause) {
        super(cause);
    }

    public JsonDeserializedException(String message, Throwable cause) {
        super(message, cause);
    }

}
