package com.github.marceloleite2604.sled.exception;

public class KeyGeneratorException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public KeyGeneratorException(String mensagem) {
		super(mensagem);
	}

	public KeyGeneratorException(String mensagem, Throwable motivo) {
		super(mensagem, motivo);
	}
}
