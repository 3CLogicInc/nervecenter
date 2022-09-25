package com.ccclogic.fusion.util.response;

import lombok.Data;

@Data
public class CommonApiResponseFromPortal {
    public static final String STATUS_SUCCESS = "success";
    public static final String STATUS_FAILURE = "failure";
    public static final String STATUS_ERROR = "error";
    private String result;
    private String message;
}
