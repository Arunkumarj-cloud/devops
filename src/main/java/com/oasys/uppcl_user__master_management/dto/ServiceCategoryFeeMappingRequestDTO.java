package com.oasys.uppcl_user__master_management.dto;

import java.io.Serializable;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ServiceCategoryFeeMappingRequestDTO implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//@NotNull(message = "103")
	private UUID serviceCategoryId;
	
	//@NotNull(message = "103")
	private Double amount;
	private String remarks; 

}
