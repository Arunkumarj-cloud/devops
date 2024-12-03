package com.oasys.uppcl_user__master_management.dto;

import java.io.Serializable;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;


@Data
public class DataTypeDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private  UUID id;
	
	@NotBlank
	 @Pattern(regexp = "^[a-z A-Z]*$", message = "Please enter Characters only")
	@Size(min = 3, message = "Please enter minimum 3 Characters ")
	private String dataTypeName;
	
	@NotNull
	private Boolean status;
}

