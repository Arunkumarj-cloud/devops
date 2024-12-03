package com.oasys.uppcl_user__master_management.dto;

import java.io.Serializable;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class BankPassbookMasterDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private UUID id;
	
	@NotBlank
	@Pattern(regexp = "^[a-zA-Z0-9&.\\\\-_ ]*$", message = "Please Enter AlphaNumeric ")
	@Size(min=3,message="Please Enter minimum 3 Characters ")
	private String name;
	
	@NotBlank
	@Size(min=3,message="Please Enter minimum 3 Characters ")
	private String description;
	
	@NotNull
	private Boolean status;
	
}
