package org.marceloleite.libs.crypt.exception;

public class KeyGeneratorException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public KeyGeneratorException(String mensagem) {
		super(mensagem);
	}

	public KeyGeneratorException(String mensagem, Throwable motivo) {
		super(mensagem, motivo);
	}
}
