package com.ccclogic.fusion.util.enums;

public enum ContentType {
    json("application/json"), xml("application/xml");

    private String value;
    ContentType(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }

}
