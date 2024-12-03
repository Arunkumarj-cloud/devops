package com.oasys.uppcl_user__master_management.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.oasys.config.Trackable;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class errorCodeDTO  implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String response;
	private String description;
	private String bhim;
	private String receipt;
	private Boolean status;
	
}