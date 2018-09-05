package org.marceloleite.encrypt.exception;

public class KeyGeneratorRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public KeyGeneratorRuntimeException(String mensagem) {
		super(mensagem);
	}

	public KeyGeneratorRuntimeException(String mensagem, Throwable motivo) {
		super(mensagem, motivo);
	}
}
