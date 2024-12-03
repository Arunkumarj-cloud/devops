package com.oasys.security.jwt;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class InvalidTokenException extends RuntimeException{

	
	private static final long serialVersionUID = -4789677120773169117L;

	
	public InvalidTokenException() {
		super();
	}
	public InvalidTokenException(String msg, Throwable t) {
		super(msg, t);
	}

	public InvalidTokenException(String msg) {
		super(msg);
	}

	public int getHttpErrorCode() {
		return 401;
	}

	public String getInvalidTokenErrorCode() {
		return "invalid_token";
	}
	
	public InvalidTokenException(Throwable cause) {
		super(cause);
	}
	
}
