package com.oasys.uppcl_user__master_management.dto;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import lombok.Data;
@Data
public class ServiceCategoryDTO {
	private UUID id;
    
	@NotNull
	private String name;

	@NotNull
	private Boolean status;
	
	private String remarks;
}
