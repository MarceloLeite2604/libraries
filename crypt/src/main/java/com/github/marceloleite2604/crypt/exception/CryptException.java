package com.github.marceloleite2604.crypt.exception;

public class CryptException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CryptException(String mensagem) {
		super(mensagem);
	}

	public CryptException(String mensagem, Throwable motivo) {
		super(mensagem, motivo);
	}
}
