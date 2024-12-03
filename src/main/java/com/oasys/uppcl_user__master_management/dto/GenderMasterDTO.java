package com.oasys.uppcl_user__master_management.dto;

import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


import lombok.Data;
@Data
public class GenderMasterDTO {
private UUID id;
	
	@NotBlank
	@Pattern(regexp = "^[a-zA-Z &.\\-_]*$", message = "Please Enter Characters Only")
	@Size(min=2,message="Please Enter Minimum 2 Characters ")
	private String name;
	
	private String remarks;
	@NotNull
	private Boolean status;
}
