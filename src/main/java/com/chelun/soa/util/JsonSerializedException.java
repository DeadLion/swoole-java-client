package com.chelun.soa.util;

/**
 * 
 * @author danson.liu
 * @version $Id: JsonSerializedException.java, v 0.1 2013-6-24 下午03:54:32 danson.liu Exp $
 */
public class JsonSerializedException extends RuntimeException {

    private static final long serialVersionUID = -5227867017380075929L;

    public JsonSerializedException(String message) {
        super(message);
    }

    public JsonSerializedException(Throwable cause) {
        super(cause);
    }

    public JsonSerializedException(String message, Throwable cause) {
        super(message, cause);
    }

}
