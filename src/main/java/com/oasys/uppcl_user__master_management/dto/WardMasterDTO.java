package com.oasys.uppcl_user__master_management.dto;

import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


import com.oasys.uppcl_user__master_management.entity.Muncipality;

import lombok.Data;

@Data
public class WardMasterDTO {

	private UUID id;

	@NotBlank
	@Size(min = 3, message = "Please enter minimum 3 characters/numbers")
	@Pattern(regexp = "^[a-zA-Z0-9 ]*$", message = "Please enter characters/numbers")
	private String wardName;

	@NotNull
	private Boolean status;

	@NotNull
	private Muncipality municipalitynameId;

}
