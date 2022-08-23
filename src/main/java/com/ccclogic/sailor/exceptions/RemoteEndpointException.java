package com.ccclogic.sailor.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class RemoteEndpointException extends RuntimeException {

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     * later retrieval by the {@link #getMessage()} method.
     */

    private HttpStatus status;

    public RemoteEndpointException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
