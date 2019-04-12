package com.github.marceloleite2604.encryptor.exception;

public class EncryptionRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EncryptionRuntimeException(String mensagem) {
		super(mensagem);
	}

	public EncryptionRuntimeException(String mensagem, Throwable motivo) {
		super(mensagem, motivo);
	}
}
