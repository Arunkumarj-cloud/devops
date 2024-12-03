package com.oasys.uppcl_user__master_management.dto;

import java.util.UUID;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class PlanMasterDTO {

	private UUID id;

	@NotBlank(message = "103")
//	@Pattern(regexp = "^[a-zA-Z0-9&.\\-_ ]*$", message = "Please enter numbers/characters only")
//	@Size(min = 3, message = "Please enter minimum 3 characters/numbers")
	private String planName;

	@NotNull(message = "103")
	private Boolean isActive;
	@NotNull(message = "103")
	private Boolean isDefault;
	//@NotNull
	//@Digits(integer = 7, fraction = 0)
	private int maxUserCount;

	//@NotBlank
	//@Size(min = 3, message = "Please enter minimum 3 characters")
	private String description;
	
	private String remarks;
	
	@NotNull(message = "103")
	private Double amount;
}
