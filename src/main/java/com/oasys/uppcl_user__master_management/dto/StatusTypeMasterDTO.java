package com.oasys.uppcl_user__master_management.dto;

import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


import lombok.Data;

@Data
public class StatusTypeMasterDTO {
	
	private UUID id;
	
	@NotBlank
	private String statusTypeName;
	  
	@NotNull
    private Boolean status;

}
