package com.github.marceloleite2604.sled;

public class SledRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public SledRuntimeException(String mensagem) {
		super(mensagem);
	}

	public SledRuntimeException(String mensagem, Throwable motivo) {
		super(mensagem, motivo);
	}
}
