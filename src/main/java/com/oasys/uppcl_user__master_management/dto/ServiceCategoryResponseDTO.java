package com.oasys.uppcl_user__master_management.dto;

import java.io.Serializable;
import java.util.UUID;



import lombok.Data;
@Data
public class ServiceCategoryResponseDTO implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UUID id;
	private String categoryName;
	private Double amount;
	private Boolean status;

}

