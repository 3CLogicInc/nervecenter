package com.ccclogic.sailor.util;

import java.util.Arrays;
import java.util.List;

public class StringUtil {

    public static List<String> convertCsvToList(String input){
        return Arrays.asList(input.trim().split(","));
    }

    public static String combineStrings(String... input){
        StringBuilder stringBuilder = new StringBuilder();
        for(String inp : input){
            stringBuilder.append(inp);
        }
        return stringBuilder.toString();
    }

    public static boolean isValid(String str){
       return str != null && !"null".equals(str) && !"".equals(str);
    }
}
