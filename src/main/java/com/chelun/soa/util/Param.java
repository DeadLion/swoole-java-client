package com.chelun.soa.util;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 
 * @author danson.liu
 * @version $Id: Param.java, v 0.1 2013-7-2 下午02:03:49 danson.liu Exp $
 */
@Target({ PARAMETER })
@Retention(RUNTIME)
public @interface Param {

    String value();

}
