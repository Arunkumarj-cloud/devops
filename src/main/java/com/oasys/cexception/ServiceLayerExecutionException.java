package com.oasys.cexception;

public class ServiceLayerExecutionException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3598859628828600953L;

	public ServiceLayerExecutionException() {
		super();
	
	}

	public ServiceLayerExecutionException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	
	}

	public ServiceLayerExecutionException(String message, Throwable cause) {
		super(message, cause);
	
	}

	public ServiceLayerExecutionException(String message) {
		super(message);
	
	}

	public ServiceLayerExecutionException(Throwable cause) {
		super(cause);
	
	}
	
}
