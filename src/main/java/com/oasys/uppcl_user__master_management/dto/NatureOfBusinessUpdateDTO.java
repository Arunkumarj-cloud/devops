package com.oasys.uppcl_user__master_management.dto;

import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


import lombok.Data;


@Data
public class NatureOfBusinessUpdateDTO {

	private UUID id;

	@NotBlank
    @Pattern(regexp = "^[a-zA-Z &.\\-_/—,( )`'’ ]*$", message = "Please Enter Alphabets and Special Characters Only")
	@Size(min = 3, message = "Please Enter Minimum 3 Characters")
	private String name;

//	@NotBlank
	private String remarks;
	
	@NotNull
	private Boolean status;
	

}
