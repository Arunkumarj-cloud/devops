package com.oasys.uppcl_user__master_management.dto;

import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


import lombok.Data;

@Data
public class FieldTypeMasterDTO {
private UUID id;
	
    @NotBlank
	private String fieldTypeName;
	
    @NotNull
	private Boolean status;
}
