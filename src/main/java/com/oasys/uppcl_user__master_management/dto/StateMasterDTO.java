package com.oasys.uppcl_user__master_management.dto;

import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class StateMasterDTO {
	private UUID id;

	@NotBlank
	@Pattern(regexp = "^[a-zA-Z&.\\-_ ]*$", message = "Please enter Characters only")
	@Size(min = 3, message = "Please enter minimum 3 characters")
	private String stateName;

	@NotNull
	private Boolean status;

	@NotBlank
	@Size(min = 2, max = 5, message = "Please enter minimum 2 characters/numbers")
	@Pattern(regexp = "^[a-zA-Z 0-9]*$", message = "Invalid State Code")
	private String stateCode;

	private Boolean deleteFlag;
	
	private String stateType;
	
	private String taxIdentificationNo;

}
