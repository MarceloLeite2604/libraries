package com.figtreelake.blimp;

public class BlimpRuntimeException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public BlimpRuntimeException(String message, Throwable cause) {
    super(message, cause);
  }

  public BlimpRuntimeException(String message) {
    super(message);
  }

}
