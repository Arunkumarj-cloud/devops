package com.oasys.uppcl_user__master_management.dto;

import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


import com.oasys.uppcl_user__master_management.entity.TalukMasterEntity;

import lombok.Data;


@Data
public class VillageMasterDTO {

	private UUID id;

	@NotBlank
	@Size(min = 3, message = "Please enter minimum 3 characters")
	@Pattern(regexp = "^[a-zA-Z &.\\-_]*$", message = "Please Enter Characters only")
	private String villageName;

	@NotNull
	private Boolean status;

	private String code;

	@NotNull
	private TalukMasterEntity talukId;

}
