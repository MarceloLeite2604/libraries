package com.github.marceloleite2604.properties.exception;

public class PropertyRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PropertyRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public PropertyRuntimeException(String message) {
		super(message);
	}
}
