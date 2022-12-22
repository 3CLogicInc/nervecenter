package com.ccclogic.nerve.exceptions;


import com.ccclogic.nerve.util.logger.Debug;
import com.ccclogic.nerve.util.logger.Info;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ErrorResponse {

    @JsonView(Info.class)
    private List<ApplicationError> errors = new ArrayList<>();

    @JsonView(Debug.class)
    private String stackTrace;

    public ErrorResponse(String errorCode, String description){
        errors.add(new ApplicationError(errorCode, description));
    }

    public ErrorResponse(List<ApplicationError> errors){
        this.errors = errors;
    }
}
