package com.github.marceloleite2604.sled.key;

public class KeyGeneratorRuntimeException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public KeyGeneratorRuntimeException(String mensagem, Throwable motivo) {
    super(mensagem, motivo);
  }

}
