package com.oasys.uppcl_user__master_management.dto;

import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.oasys.uppcl_user__master_management.entity.NatureOfBusinessMasterEntity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class StoreCategoryDTO {
	
	private UUID id;

	@NotBlank
	@Pattern(regexp = "^[a-zA-Z0-9&.\\-_ ]*$", message = "Please Enter AlphaNumeric Only")
	@Size(min = 3, message = "Please Enter Minimum 3 Characters")
	private String storeCategoryName;
	@NotNull
	private NatureOfBusinessMasterEntity natureOfBusinessId;

	@NotNull
	private Boolean status;

	@NotBlank
	@Size(min = 3, message = "Please Enter Minimum 3 Characters")
	private String description;
	
	

}
