package com.ccclogic.sailor.util;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public abstract class RequestUtil {

    public static Map<String, String> parseFromPath(String path){
        Map<String, String> requestMap = new HashMap<>();
        StringTokenizer tokenizer = new StringTokenizer(path, "/");
        while (tokenizer.hasMoreElements()){
            String key = tokenizer.nextToken();
            String value = tokenizer.hasMoreElements()? tokenizer.nextToken(): "";
            requestMap.put(key,value);
        }

        return requestMap;
    }
}
