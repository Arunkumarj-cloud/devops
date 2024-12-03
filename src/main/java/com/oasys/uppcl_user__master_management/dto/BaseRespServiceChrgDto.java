package com.oasys.uppcl_user__master_management.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.oasys.uppcl_user__master_management.response.ResponseMessageConstant;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseRespServiceChrgDto {
	Integer statusCode = 500;

	String message;

	Object responseContents;

	List<ServiceChargesDTO> responseContent;
	
	public BaseRespServiceChrgDto(ResponseMessageConstant responseMessageConstant) {
		
		statusCode = responseMessageConstant.getErrorCode();
		message = responseMessageConstant.getMessage();
	}


	
	public BaseRespServiceChrgDto() {
		
	}
	
	public BaseRespServiceChrgDto(Integer statusCode, String message) {
		this.statusCode = statusCode;
		this.message = message;
	}
}
