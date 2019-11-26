package com.github.marceloleite2604.util.file;

public class FileUtilRuntimeException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public FileUtilRuntimeException(String message, Throwable cause) {
    super(message, cause);
  }

  public FileUtilRuntimeException(String message) {
    super(message);
  }

}
