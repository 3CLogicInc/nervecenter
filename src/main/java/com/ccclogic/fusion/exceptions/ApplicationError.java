package com.ccclogic.fusion.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class ApplicationError {

	private String errorCode;

	private String errorMessage;

    @JsonIgnore
    private Object[] args;

    public ApplicationError(String errorCode) {
        this.errorCode = errorCode;
    }

	public ApplicationError(String errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

    public ApplicationError(String errorCode, Object[] args) {
        this.errorCode = errorCode;
        this.args = args;
    }

}
