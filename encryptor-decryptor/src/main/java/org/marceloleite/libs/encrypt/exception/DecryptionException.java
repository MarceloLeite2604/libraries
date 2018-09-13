package org.marceloleite.libs.encrypt.exception;

public class DecryptionException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DecryptionException(String mensagem) {
		super(mensagem);
	}

	public DecryptionException(String mensagem, Throwable motivo) {
		super(mensagem, motivo);
	}
}
