package com.ccclogic.nerve.util.constants;

public interface RequestErrorCodes {

    String jsonPayloadInvalid = "request_json_payload_invalid";
    String requestPayloadInvalid = "request_payload_invalid";
    String httpMethodNotAllowed = "http_method_not_allowed"; //No message required for this, as exception message is good enough "Request method 'POST' not supported"
    String missingRequiredQueryParamater = "missing_required_query_parameter";
    String missingPathVariable = "missing_path_variable";
    String mediaTypeNotSupported = "media_type_not_supported";
    String invalidRequestUrl = "invalid_requested_url";
    String fileSizeExceeded = "invalid_file_size";
    String invalidPathVariableType = "invalid_path_variable_type";
}
