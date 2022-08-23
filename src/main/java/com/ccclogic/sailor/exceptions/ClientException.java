package com.ccclogic.sailor.exceptions;
import java.util.List;

public class ClientException extends RuntimeException {

    private static final long serialVersionUID = 3809864003171128496L;

    private List<ApplicationError> errors;
    private Object[] args;

    public ClientException(String message) {
        super(message);
    }

    public ClientException(String message, Object[] args) {
        super(message);
        this.args = args;
    }

    public ClientException(List<ApplicationError> errors) {
        this.errors = errors;
    }

    public List<ApplicationError> getErrors() {
        return this.errors;
    }

    public Object[] getArgs() {
        return this.args;
    }
}