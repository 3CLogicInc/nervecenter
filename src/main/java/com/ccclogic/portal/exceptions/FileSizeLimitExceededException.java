package com.ccclogic.portal.exceptions;

public class FileSizeLimitExceededException extends RuntimeException {

    public FileSizeLimitExceededException(String message) {
        super(message);
    }
}
