package com.oasys.uppcl_user__master_management.dto;

import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
@Data
public class EducationDTO {
	private static final long serialVersionUID = 1L;
	
	private UUID id;
	
	@NotBlank
	//@Pattern(regexp = "^[a-zA-Z &.\\-_]*$", message = "Please Enter Characters Only")
	@Size(min=2,message="Please Enter Minimum 2 Characters ")
	private String name;
	
	@NotNull
	private Boolean status;
	
	private String remarks;
	  

}


