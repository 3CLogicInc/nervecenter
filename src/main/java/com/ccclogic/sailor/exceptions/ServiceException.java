package com.ccclogic.sailor.exceptions;

public class ServiceException extends RuntimeException{

	private static final long serialVersionUID = 432808193477654305L;

	public ServiceException(String message) {
		super(message);
	}
	
}
