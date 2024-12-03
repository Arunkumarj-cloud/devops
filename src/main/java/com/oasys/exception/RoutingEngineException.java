package com.oasys.exception;

import com.oasys.uppcl_user__master_management.response.ResponseMessageConstant;

public class RoutingEngineException extends RuntimeException {

	/**
	 * 
	 */
	private  Object value;
	private static final long serialVersionUID = 3598859628828600953L;
	private ResponseMessageConstant responseMessageConstant;

	public RoutingEngineException(ResponseMessageConstant responseMessageConstant) {
		super(responseMessageConstant.getMessage());
		this.responseMessageConstant = responseMessageConstant;
	}
	
	public RoutingEngineException(ResponseMessageConstant responseMessageConstant, Object value) {
		super(responseMessageConstant.getMessage(value.toString()));
		this.responseMessageConstant = responseMessageConstant;
		this.value = value.toString();
	}

	public RoutingEngineException(ResponseMessageConstant responseMessageConstant,Throwable cause) {
		super(responseMessageConstant.getMessage(),cause);
		this.responseMessageConstant = responseMessageConstant;
		
	}
	public RoutingEngineException(Object value)
	{
	
		this.value=value;
	}


	public RoutingEngineException(String message, Throwable cause) {
		super(message, cause);
	
	}

	public RoutingEngineException(String message) {
		super(message);
	
	}

	public RoutingEngineException(Throwable cause) {
		super(cause);
	}


	public Object getValue() {
		return value;
	}


	public ResponseMessageConstant getResponseMessageConstant() {
		return responseMessageConstant;
	}

}
