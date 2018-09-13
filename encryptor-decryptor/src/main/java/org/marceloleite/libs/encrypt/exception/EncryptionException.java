package org.marceloleite.libs.encrypt.exception;

public class EncryptionException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EncryptionException(String mensagem) {
		super(mensagem);
	}

	public EncryptionException(String mensagem, Throwable motivo) {
		super(mensagem, motivo);
	}
}
