package com.ccclogic.fusion.config;


import com.ccclogic.fusion.exceptions.*;
import com.ccclogic.fusion.util.JsonUtil;
import com.ccclogic.fusion.util.StringUtil;
import com.ccclogic.fusion.util.constants.GenericErrorCodes;
import com.ccclogic.fusion.util.constants.RequestErrorCodes;
import com.ccclogic.fusion.util.logger.Debug;
import com.ccclogic.fusion.util.logger.Info;
import com.fasterxml.jackson.core.JsonParseException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalErrorHandler {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ErrorCodeMessages errorCodeMessages;

	@ExceptionHandler(value = { ClientException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	protected String handleClientException(ClientException e, HttpServletRequest request) {
		ErrorResponse response = null;
		if(!CollectionUtils.isEmpty(e.getErrors())){
			for (ApplicationError error : e.getErrors()) {
				if (error.getArgs() != null && error.getArgs().length > 0) {
					error.setErrorMessage(errorCodeMessages.getMessage(error.getErrorCode(), error.getArgs()));
				} else {
					error.setErrorMessage(errorCodeMessages.getMessage(error.getErrorCode()));
				}
			}
			response = new ErrorResponse(e.getErrors());
		}
		else {
			if (e.getArgs() != null && e.getArgs().length > 0) {
				response = new ErrorResponse(e.getMessage(), errorCodeMessages.getMessage(e.getMessage(), e.getArgs()));
			} else {
				response = new ErrorResponse(e.getMessage(), errorCodeMessages.getMessage(e.getMessage()));
			}
		}
		return getErrorJson(request, response, e);
	}

	@ExceptionHandler(value = { ServiceException.class })
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	protected String handleServiceException(ServiceException e, HttpServletRequest request) {
		return getErrorJson(request, e);
	}

	@ExceptionHandler(value = { NoHandlerFoundException.class })
	@ResponseStatus(HttpStatus.NOT_FOUND)
	protected String handleNoHandlerFoundExceptions(NoHandlerFoundException e, HttpServletRequest request) {
		return getErrorJson(request, e);
	}

	@ExceptionHandler(value = { IllegalArgumentException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	protected String handleIllegalArgumentExceptions(Throwable e, HttpServletRequest request){
		return getErrorJson(request, e);
	}

//	@ExceptionHandler(value = { AccessDeniedException.class })
//	@ResponseStatus(HttpStatus.FORBIDDEN)
//	protected String handleAccessDeniedException(AccessDeniedException e, HttpServletRequest request) {
//		return getErrorJson(request, e);
//	}

	@ExceptionHandler(value = {RemoteEndpointException.class})
	@ResponseStatus(HttpStatus.FORBIDDEN)
	protected String handleRemoteEndpointException(RemoteEndpointException e, HttpServletRequest request) {
		return getErrorJson(request, e);
	}

	@ExceptionHandler(value = { Throwable.class })
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	protected String handleUnhandledExceptions(Throwable e, HttpServletRequest request) {
		ErrorResponse unknownErrorResponse = new ErrorResponse(GenericErrorCodes.unknowError,
				errorCodeMessages.getMessage(GenericErrorCodes.unknowError));
		return getErrorJson(request, unknownErrorResponse, e);
	}

	@ExceptionHandler(value = { MethodArgumentNotValidException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	protected String handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
		List<ApplicationError> errors = new ArrayList<>();
		BindingResult result = e.getBindingResult();
		if(result.getErrorCount() > 0){
			for(FieldError fieldError: result.getFieldErrors()){
				ApplicationError error= new ApplicationError(fieldError.getDefaultMessage(),
						errorCodeMessages.getMessage(fieldError.getDefaultMessage()));
				errors.add(error);
			}
		}
		ErrorResponse response = new ErrorResponse(errors);
		return getErrorJson(request, response, e);
	}

	@ExceptionHandler(value = { ResourceNotFoundException.class })
	@ResponseStatus(HttpStatus.NOT_FOUND)
	protected String handleNotFoundExceptions(Throwable e, HttpServletRequest request) {
		return getErrorJson(request, e);
	}

	@ExceptionHandler(value = { HttpMessageNotReadableException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	protected String handleHttpMessageNotReadableException(HttpMessageNotReadableException e, HttpServletRequest request) {
		ErrorResponse response = null;
		String[] errorMessages = e.getMessage().split(";");
		if(errorMessages != null && errorMessages.length >= 1 ) {
			response = new ErrorResponse(RequestErrorCodes.jsonPayloadInvalid,
					errorMessages[0]);
		}
		else{
			response = new ErrorResponse(RequestErrorCodes.requestPayloadInvalid,
					errorCodeMessages.getMessage(RequestErrorCodes.requestPayloadInvalid));
		}
		return getErrorJson(request, response, e);
	}

    @ExceptionHandler(value = {HttpMediaTypeNotSupportedException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected String handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e, HttpServletRequest request) {

        ErrorResponse response = null;
        response = new ErrorResponse(RequestErrorCodes.mediaTypeNotSupported, errorCodeMessages.getMessage(RequestErrorCodes.mediaTypeNotSupported));
        return getErrorJson(request, response, e);
    }

	@ExceptionHandler(value = {MaxUploadSizeExceededException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	protected String handleHttpMediaTypeNotSupportedException(MaxUploadSizeExceededException e, HttpServletRequest request) {

		ErrorResponse response = null;
		response = new ErrorResponse(RequestErrorCodes.fileSizeExceeded, errorCodeMessages.getMessage(RequestErrorCodes.fileSizeExceeded));
		return getErrorJson(request, response, e);
	}

    @ExceptionHandler(value = {HttpMessageConversionException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected String handleHttpMessageConversionException(HttpMessageConversionException e, HttpServletRequest request) {
        ErrorResponse response = null;
        if (e.getCause() instanceof JsonParseException) {
            response = new ErrorResponse(RequestErrorCodes.jsonPayloadInvalid,
                    errorCodeMessages.getMessage(RequestErrorCodes.jsonPayloadInvalid));
        } else {
            response = new ErrorResponse(RequestErrorCodes.requestPayloadInvalid,
                    errorCodeMessages.getMessage(RequestErrorCodes.requestPayloadInvalid));
        }
        return getErrorJson(request, response, e);
    }
//	HttpMessageConversionException

	@ExceptionHandler(value = { HttpRequestMethodNotSupportedException.class })
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	protected String handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
		ErrorResponse response = new ErrorResponse(RequestErrorCodes.httpMethodNotAllowed, e.getMessage());
		return getErrorJson(request, response, e);
	}

	@ExceptionHandler(value = { ResourceConflictException.class })
	@ResponseStatus(HttpStatus.CONFLICT)
	protected String handleResourceConflictException(ResourceConflictException e, HttpServletRequest request) {
		return getErrorJson(request, e);
	}

	@ExceptionHandler(value = { MissingServletRequestParameterException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	protected String handleMissingServletRequestParameterException(MissingServletRequestParameterException e, HttpServletRequest request) {
		ErrorResponse response = new ErrorResponse(RequestErrorCodes.missingRequiredQueryParamater,
				errorCodeMessages.getMessage(RequestErrorCodes.missingRequiredQueryParamater, new Object[]{e.getParameterName()}));
		return getErrorJson(request, response, e);
	}

	@ExceptionHandler(value = {MissingPathVariableException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	protected String handleMissingPathVariableExceptionn(MissingPathVariableException e, HttpServletRequest request) {

		ErrorResponse response = new ErrorResponse(RequestErrorCodes.missingPathVariable,
				errorCodeMessages.getMessage(RequestErrorCodes.missingPathVariable, new Object[]{e.getVariableName()}));
		return getErrorJson(request, response, e);
	}

	@ExceptionHandler(value = {MethodArgumentTypeMismatchException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	protected String handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e, HttpServletRequest request) {

		ErrorResponse response = new ErrorResponse(RequestErrorCodes.invalidPathVariableType,
				errorCodeMessages.getMessage(RequestErrorCodes.invalidPathVariableType, new Object[]{e.getName()}));
		return getErrorJson(request, response, e);
	}

	protected String getErrorJson(HttpServletRequest request, ErrorResponse response, Throwable e){
		logger.error(e.getMessage(), e);
		if(response == null){
			if( !StringUtil.isValid( e.getMessage() )){
				response = new ErrorResponse(GenericErrorCodes.unknowError, errorCodeMessages.getMessage(GenericErrorCodes.unknowError));
			} else {
				response = new ErrorResponse(e.getMessage(), errorCodeMessages.getMessage(e.getMessage()));
			}
		}
		if((request.getParameter("debug") != null && "true".equals(request.getParameter("debug")))) {
			response.setStackTrace(ExceptionUtils.getStackTrace(e));
			return JsonUtil.convertObjectWithViewToJsonString(response, Debug.class);
		}
		else{
			return JsonUtil.convertObjectWithViewToJsonString(response, Info.class);
		}
	}

	protected String getErrorJson(HttpServletRequest request, Throwable e){
		return getErrorJson(request, null, e);
	}
}
