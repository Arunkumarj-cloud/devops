package com.oasys.cexception;

import java.util.List;

import com.oasys.uppcl_user__master_management.response.ResponseMessageConstant;


public class InvalidRequestException extends RuntimeException{

	/**
	 * 
	 */
	private  Object value;
	private static final long serialVersionUID = 3598859628828600953L;
	private ResponseMessageConstant responseMessageConstant;
	private Object[] objects;
    private List<String> messages;
	
	public InvalidRequestException(ResponseMessageConstant responseMessageConstant,Object... objects) {
		super(responseMessageConstant.getMessage(objects));
		this.responseMessageConstant = responseMessageConstant;
		this.objects=objects;
		
	}

	public InvalidRequestException(ResponseMessageConstant responseMessageConstant,Throwable cause) {
		super(responseMessageConstant.getMessage(),cause);
		this.responseMessageConstant = responseMessageConstant;
		
	}
	
	public InvalidRequestException(List<String> messages) {
		super();
		this.messages=messages;
	}
	
	public Object getValue() {
		return value;
	}


	public ResponseMessageConstant getResponseMessageConstant() {
		return responseMessageConstant;
	}

	public Object[] getObjects() {
		return objects;
	}

	public List<String> getMessages() {
		return messages;
	}

	

	


}
