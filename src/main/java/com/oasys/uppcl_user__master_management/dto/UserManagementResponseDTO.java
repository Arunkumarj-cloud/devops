package com.oasys.uppcl_user__master_management.dto;

import java.io.Serializable;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter	
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserManagementResponseDTO implements Serializable  {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer statusCode;

	private String message;

	private ResponseContent responseContent;
}
