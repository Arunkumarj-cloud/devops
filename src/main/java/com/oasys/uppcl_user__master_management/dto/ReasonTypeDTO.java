package com.oasys.uppcl_user__master_management.dto;

import java.util.Date;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class ReasonTypeDTO {

	private UUID id;

	@NotBlank
	@Pattern(regexp = "^[a-zA-Z0-9&.\\-_ ]*$", message = "Please Enter AlphaNumeric Only")
	@Size(min = 3, message = "Please enter minimum 3 characters")
	private String reasonType;

	@NotBlank
	@Size(min = 3, message = "Please enter minimum 3 characters")
	private String description;

	@NotNull
	private Boolean status;
	private Date createdDate;
	
	private String remarks;

}
