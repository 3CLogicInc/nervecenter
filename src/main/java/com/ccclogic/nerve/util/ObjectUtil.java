package com.ccclogic.nerve.util;

import java.lang.reflect.Field;

public class ObjectUtil {
    @SuppressWarnings("unchecked")
    public static <T> T mergeObjects(T first, T second, boolean override) throws IllegalAccessException, InstantiationException {
        Class<?> clazz = first.getClass();
        Field[] fields = clazz.getDeclaredFields();
        Object returnValue = clazz.newInstance();
        for (Field field : fields) {
            field.setAccessible(true);
            Object value1 = field.get(first);
            Object value2 = field.get(second);
            Object value = (value1 != null) ? value1 : value2;

            if(override && value2 != null){
                value = value2;
            }
            field.set(returnValue, value);
        }
        return (T) returnValue;
    }
}
