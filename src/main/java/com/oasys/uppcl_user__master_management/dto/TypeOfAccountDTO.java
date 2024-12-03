package com.oasys.uppcl_user__master_management.dto;

import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;



import lombok.Data;

@Data
public class TypeOfAccountDTO {

	private UUID id;

	@NotBlank
	//@Size(min = 3, message = "Please enter minimum 3 characters")
	//@Pattern(regexp = "^[a-zA-Z0-9&.\\-_ ]*$", message = "Please enter characters/numbers")
	private String accountType;
	@NotNull
	private String remarks;
	@NotNull
	private Boolean status;

}
