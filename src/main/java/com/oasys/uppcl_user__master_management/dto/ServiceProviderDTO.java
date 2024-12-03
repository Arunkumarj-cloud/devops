package com.oasys.uppcl_user__master_management.dto;

import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.oasys.uppcl_user__master_management.entity.ServiceCategoryEntity;

import lombok.Data;

@Data
public class ServiceProviderDTO {

	private UUID id;
	
	@NotBlank	
	private String serviceProviderName;

	@NotNull
	private ServiceCategoryEntity serviceCategoryId;
	@NotNull
	private Boolean status;
	
	private String remarks;
}
