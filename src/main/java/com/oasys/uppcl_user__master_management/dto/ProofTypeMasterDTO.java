package com.oasys.uppcl_user__master_management.dto;

import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ProofTypeMasterDTO {

	private UUID id;

	
	@NotBlank
	@Pattern(regexp = "^[a-zA-Z 0-9]*$", message = "Please Enter AlphaNumeric only")
	@Size(min = 3, message = "Please Enter Minimum 3 characters")
	private String name;

	@NotBlank
	@Size(min = 3, message = "Please Enter Minimum 3 Characters")
	private String description;

	@NotNull
	private Boolean status;

}
