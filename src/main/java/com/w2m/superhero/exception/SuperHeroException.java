package com.w2m.superhero.exception;

public class SuperHeroException extends Exception {
	private static final long serialVersionUID = 8674291358056901497L;

	public SuperHeroException(String message) {
		super(message);
	}

	public SuperHeroException(Throwable cause) {
		super(cause);
	}

	public SuperHeroException(String message, Throwable cause) {
		super(message, cause);
	}
}
