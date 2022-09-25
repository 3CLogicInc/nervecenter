package com.ccclogic.fusion.util.response;

import java.lang.reflect.Field;
import java.util.*;

public class ResponseFieldExtractor {

    public static <T> List<Map<String, Object>> extractKeys(Collection<T> collection, List<String> fields) throws IllegalAccessException {
        List<Map<String,Object>> rxtracted = new ArrayList<>();
        for(T entity: collection){
            rxtracted.add(extractKey(entity, fields));
        }

        return rxtracted;
    }

    public static <T> Map<String,Object> extractKey(T entity, List<String> fields) throws IllegalAccessException {
        Field[] declaredFields = entity.getClass().getDeclaredFields();
        Map<String, Object> fieldValueMap = new HashMap<>();

        for (Field field : declaredFields){
            if(fields.contains(field.getName())){
                //To access private fields
                field.setAccessible(true);
                fieldValueMap.put(field.getName(), field.get(entity));
            }
        }
        return fieldValueMap;
    }
}
