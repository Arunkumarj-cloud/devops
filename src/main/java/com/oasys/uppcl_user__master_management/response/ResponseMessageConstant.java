package com.oasys.uppcl_user__master_management.response;

import java.util.Locale;
import java.util.Optional;

import org.springframework.context.MessageSource;



public enum ResponseMessageConstant {
	SUCCESS_RESPONSE(200,200),
	CREATE_SUCCESS_RESPONSE(200,201),
	DELET_RESPONSE(200,202),
	UPDAE_RESPONSE(200,203),
	SEARCH_RESPONSE(200,204),
	CREATED(201,201),
	NON_AUTHORITATIVE_INFORMATION(203,203),
	EMPTY_DATA(204,2014),
	BAD_REQUEST(400,400),
	ACCESS_DENIED(401,401),
	AUTHENTICATION_FAILED(401,401),
	NOT_FOUND(404,404),
	METHOD_NOT_ALLOWED(405,405),
	FAILURE_RESPONSE(417,417),
	INVALID_USER(417,126),
	IP_ACCEES_DENIED(417,158),
	BAD_CREDENTIAL(204,127),
	INTERNAL_SERVER_ERROR(500,500),
	FILE_EXTENSION_NOT_ALLOWED(400, 466),
	ALREADY_EXISTS(409,409),
	MUST_BE_SAME_STATE(400,435),
	MUST_BE_SAME_DISTRICT(400,436),
	INVALID_REQUEST_WITH_ATRIBUTE(400,400),
	INVALID_REQUEST(400,401),
	DATA_VIOLATION_WITH_ATRIBUTE(409,409),
	DATA_VIOLATION(409,410),
	NO_RECOERD_FOUND(404,125),
	DELETE_PRIVIELGE_ERROR(108, 108), 
	DELETE_ROLE_ERROR(109, 109),
	MANDATORY_PARAMETER_MISSING(400, 103),
	ALREADY_EXIST(400,448),
	INVALID_REQUEST_PARAMETER(400,105),
	PLAN_SERVICE_MAPPING_ALREADY_EXIST(400,449),
	PLAN_SERVICE_MAPPING_DOES_NOT_EXIST(450,450),
	FAILED_TO_DELETE(451,451),
	PLAN_ALREADY_EXIST(450,452),
	PLAN_AMOUNT_CANNOT_LESS_THAN_LOWER_PRIORITY_PLAN(450,453),
	PLAN_AMOUNT_CANNOT_GREATER_THAN_HIGHER_PRIORITY_PLAN(450,454),
	NEW_PLAN_AMOUNT_SHOULD_BE_GREATER_THAN_HIGHER_PRIORITY_PLAN(450,455),
	SERVICE_DOES_NOT_EXIST(400,456),
	VALUE_OUT_OF_RANGE(400,457),
	SERVICE_FEE_MAPPING_ALREADY_EXIST(400,458),
	SERVICE_FEE_MAPPING_DOES_NOT_EXIST(400, 459),
	FAILED_TO_UPDATE(417, 460),
	FAILED_TO_ADD(417, 461),
	NO_CHANGE_FOUND_TO_UPDATE(400, 462),
	DEFAULT_PACKAGE_ALREADY_EXIST(400, 463),
	SERVICE_FEE_MAPPING_NOT_CONFIGURED(400, 464),
	SERVICES_WITH_AMOUNT_ZERO_IS_ALLOWED_IN_DEFAULT_PACKAGE(400, 465),
	SERVICES_LISTING_WITH_AMOUNT_GREATER_THAN_ZERO(400, 466),
	SERVICES_AMOUNT_GREATER_THAN_ZERO_NOT_ALLOWED(400, 467),
	SERVICE_CATEGORY_MISSING(400, 468),
	FAILED_TO_RETRIEVE(500, 469),
	DEFAULT_PACKAGE_AMOUNT_GREATER_THAN_ZERO(400, 470),
	AMOUNT_CANNOT_BE_NEGATIVE(400, 471),
	FIELD_LENGTH_EXCEEDED(400, 472),
	MINIMUM_FIELD_LENGTH_VALIDATION_FAILURE(400,473),
	INSUFFICIENT_SERVICE_COUNT(400, 474),
	PACKAGE_SERVICE_MAPPING_ALREADY_EXIST(400, 475),
	SAME_AMOUNT_ALREADY_CONFIGURED(400,476),
	 FIELD_REQUIRED_MIN_LENGTH(400, 477),
	 PACKAGE_AMOUNT_GREATER_THAN_TOTAL_SERVICE_AMOUNT(400,478);
	
	private int errorCode;
	private int messageCode;
	
	private ResponseMessageConstant(int errorCode,int messageCode) {
		this.errorCode=errorCode;
		this.messageCode=messageCode;
	}
	private MessageSource messageSource;
	
	public int getErrorCode() {
		return errorCode;
	}
	
	
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	public String getMessage() 
	{		
		return messageSource.getMessage(String.valueOf(messageCode),new Object[]{},Locale.ENGLISH);
	}
	public String getMessage(Object[] arg1) {
		return messageSource.getMessage(String.valueOf(messageCode), arg1, Locale.ENGLISH);
	}

	public String getMessage(String value) {
		Object[]ob=new Object[1];
		ob[0]=value;
		return messageSource.getMessage(String.valueOf(messageCode), ob, Locale.ENGLISH);
	}
	public static Optional<ResponseMessageConstant> getResponseMessageContext(int code)
	{
		for(ResponseMessageConstant responseMessageConstant:ResponseMessageConstant.values())
		{
				if(responseMessageConstant.messageCode==code)
				{
					return  Optional.of(responseMessageConstant);
				}	
		}
		return Optional.of(null);
		
	}

}
