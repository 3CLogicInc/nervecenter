package com.ccclogic.nerve.exceptions;

public class SecurityException extends RuntimeException{

	private static final long serialVersionUID = -6158889049471258138L;
	
	public SecurityException(String message) {
		super(message);
	}
	
	public SecurityException(String message, Throwable t){
		super(message, t);
	}
	
}
