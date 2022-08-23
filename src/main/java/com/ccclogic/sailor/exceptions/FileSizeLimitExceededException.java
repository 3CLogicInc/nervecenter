package com.ccclogic.sailor.exceptions;

public class FileSizeLimitExceededException extends RuntimeException {

    public FileSizeLimitExceededException(String message) {
        super(message);
    }
}
