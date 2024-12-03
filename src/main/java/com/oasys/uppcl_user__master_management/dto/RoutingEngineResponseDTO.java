package com.oasys.uppcl_user__master_management.dto;

import lombok.Data;

@Data
public class RoutingEngineResponseDTO {

	private String status;
	private String serviceCategoryId;
	private String subCategoryId;
	private String serviceProviderId;
	
	private String serviceCategoryName;
	private String subCategoryName;
	private String serviceProviderName;
	
}
