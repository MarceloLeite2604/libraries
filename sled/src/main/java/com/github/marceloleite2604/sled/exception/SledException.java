package com.github.marceloleite2604.sled.exception;

public class SledException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public SledException(String mensagem) {
		super(mensagem);
	}

	public SledException(String mensagem, Throwable motivo) {
		super(mensagem, motivo);
	}
}
