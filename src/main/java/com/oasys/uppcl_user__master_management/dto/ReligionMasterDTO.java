package com.oasys.uppcl_user__master_management.dto;

import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class ReligionMasterDTO {

	private UUID id;

	@NotBlank
	@Pattern(regexp = "^[a-zA-Z ]*$", message = "Please enter characters only")
	@Size(min = 3, message = "Please enter minimum 3 characters")
	private String name;
private String remarks;
	@NotNull
	private Boolean status;

}
