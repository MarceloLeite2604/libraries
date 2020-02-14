package com.figtreelake.sled;

public class SledRuntimeException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public SledRuntimeException(String message) {
    super(message);
  }

  public SledRuntimeException(String message, Throwable cause) {
    super(message, cause);
  }

}
