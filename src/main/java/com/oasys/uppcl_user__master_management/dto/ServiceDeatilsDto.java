package com.oasys.uppcl_user__master_management.dto;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceDeatilsDto {

	@NotNull(message = "103")
	private UUID serviceId;
	
	private UUID serviceProviderId;
	
	@NotNull(message = "103")
	private UUID slabId;
	
	@NotNull(message = "103")
	private UUID subCategoryId;
	
	
}
