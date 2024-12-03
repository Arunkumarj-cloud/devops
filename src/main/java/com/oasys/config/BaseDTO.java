package com.oasys.config;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.oasys.uppcl_user__master_management.response.ResponseMessageConstant;

import lombok.Data;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	Integer statusCode = 500;

	String message;

	Object responseContent;

	List<?> responseContents;
	
	Integer pageNo;
	
	Integer PageSize;
	
	Long totalRecords;
	
	Integer NumberOfElements;
	
	Integer totalPages;
	private Integer errorCode; // 00
	private String errorDescription; // success /failure
	private String userDisplayMesg; // User customMeaasge
	private Integer duplicateResponseCodeCount;
	private Integer newResponseCodeCount;
//	public BaseDTO() {
//		
//		statusCode = ResponseMessageConstant.SUCCESS_RESPONSE.getErrorCode();
//		message = ResponseMessageConstant.SUCCESS_RESPONSE.getMessage();
//	}
	public BaseDTO(ResponseMessageConstant responseMessageConstant) {
		
		statusCode = responseMessageConstant.getErrorCode();
		message = responseMessageConstant.getMessage();
	}

//	public BaseDTO(Integer statusCode, String message) {
//		this.statusCode = statusCode;
//		this.message = message;
//	}
	
	public BaseDTO() {
		
	}
	
	public BaseDTO(Integer statusCode, String message) {
		this.statusCode = statusCode;
		this.message = message;
	}
	

}
