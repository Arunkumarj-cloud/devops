package com.oasys.uppcl_user__master_management.dto;

import java.io.Serializable;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


import lombok.Data;

@Data
public class FieldMasterDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private UUID id;
	
	@NotBlank
	private String fieldName;
	
	@NotNull
	private Boolean status;
	


}
