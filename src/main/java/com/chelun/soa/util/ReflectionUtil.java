package com.chelun.soa.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;

/**
 * @author danson.liu
 * @version $Id: ReflectionUtil.java, v 0.1 2013-6-24 下午06:29:03 danson.liu Exp $
 */
public final class ReflectionUtil {
    public static Field[] getNonstaticFields(Class<?> clazz) {
        Field[] fields = getFields(clazz);
        List<Field> nonstatics = new ArrayList<Field>(fields.length);
        for (Field field : fields) {
            if (!Modifier.isStatic(field.getModifiers())) {
                nonstatics.add(field);
            }
        }
        return nonstatics.toArray(new Field[nonstatics.size()]);
    }

    public static Field[] getFields(Class<?> clazz) {
        return getFields(clazz, Object.class);
    }

    public static Field[] getFields(Class<?> clazz, Class<?> stopClazz) {
        Field[] fields = clazz.getDeclaredFields();
        for (Class<?> superClass = clazz.getSuperclass(); superClass != null && superClass != stopClazz; superClass = superClass.getSuperclass()) {
            fields = (Field[]) ArrayUtils.addAll(fields, superClass.getDeclaredFields());
        }
        return fields;
    }

}
