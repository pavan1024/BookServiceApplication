package com.epam.exception;

public class NoBooksException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String message;
	public NoBooksException(String message){
		super(message);
	}

}
