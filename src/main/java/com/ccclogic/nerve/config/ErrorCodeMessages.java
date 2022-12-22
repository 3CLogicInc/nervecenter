package com.ccclogic.nerve.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class ErrorCodeMessages {

    @Autowired
    private MessageSource messageSource;

    public String getMessage(String key){
        return getMessage(key, null);
    }

    public String getMessage(String key, Object[] args){
        return messageSource.getMessage(key, args, null);
    }

}
