package com.oasys.uppcl_user__master_management.dto;

import java.io.Serializable;
import java.util.UUID;


import lombok.Data;
@Data
public class ServiceCategoryFeeMappingResponseDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String serviceCategoryName;
	private UUID serviceCategoryId;
	private Double amount;
	private String createdDate;
	private String modifiedDate;
	private Boolean isActive;


}
