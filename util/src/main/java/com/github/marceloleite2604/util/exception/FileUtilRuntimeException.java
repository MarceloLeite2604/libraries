package com.github.marceloleite2604.util.exception;

public class FileUtilException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FileUtilException(Throwable cause, String message) {
		super(message, cause);
	}

	public FileUtilException(String message) {
		super(message);
	}

}
