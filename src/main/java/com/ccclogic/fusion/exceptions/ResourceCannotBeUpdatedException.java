package com.ccclogic.fusion.exceptions;

public class ResourceCannotBeUpdatedException extends RuntimeException {
    
    String message = null;
    public ResourceCannotBeUpdatedException(String message) {
        this.message = message;
    }
}