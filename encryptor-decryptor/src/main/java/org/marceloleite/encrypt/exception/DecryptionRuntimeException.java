package org.marceloleite.encrypt.exception;

public class DecryptionRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DecryptionRuntimeException(String mensagem) {
		super(mensagem);
	}

	public DecryptionRuntimeException(String mensagem, Throwable motivo) {
		super(mensagem, motivo);
	}
}
