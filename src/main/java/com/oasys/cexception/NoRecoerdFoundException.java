package com.oasys.cexception;

public class NoRecoerdFoundException extends RuntimeException {

	private static final long serialVersionUID = 3598859628828600953L;

	public NoRecoerdFoundException() {
		super();
	
	}

	public NoRecoerdFoundException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	
	}

	public NoRecoerdFoundException(String message, Throwable cause) {
		super(message, cause);
	
	}

	public NoRecoerdFoundException(String message) {
		super(message);
	
	}

	public NoRecoerdFoundException(Throwable cause) {
		super(cause);
	
	}
}
