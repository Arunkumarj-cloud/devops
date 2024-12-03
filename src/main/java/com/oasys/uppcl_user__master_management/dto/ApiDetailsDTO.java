package com.oasys.uppcl_user__master_management.dto;

import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class ApiDetailsDTO {
	
	private UUID id;

	@NotBlank
	@Pattern(regexp = "^[a-zA-Z0-9.\\-_/ ]*$", message = "Please Enter Characters Only")
	@Size(min = 3, message = "Please Enter Minimum 3 Characters")
	private String url;

	@NotBlank
	@Pattern(regexp = "^[a-zA-Z0-9&.\\-_ ]*$", message = "Please Enter Characters only")
	@Size(min = 3, message = "Please Enter Minimum 3 Characters ")
	private String apiName;

	@NotBlank
	@Pattern(regexp = "^[a-z A-Z]*$", message = "Please Enter Characters only")
	@Size(min = 3, message = "Please Enter Minimum 3 Characters ")
	private String requestMethod;

	@NotBlank
	@Pattern(regexp = "^[a-zA-Z.\\-_/ ]*$", message = "Please Enter Characters Only")
	@Size(min = 3, message = "Please Enter Minimum 3 Characters ")
	private String contentType;

	@NotBlank
	@Size(min = 3, message = "Please Enter Minimum 3 Characters ")
	private String responseBodyType;
	// private String isActive;
	// private String isDeleted;
	@NotNull
	private Boolean status;

	private String description;

	@NotBlank
	@Pattern(regexp = "^[a-zA-Z0-9&.\\-_ ]*$", message = "Please Enter AlphaNumeric only")
	@Size(min = 3, message = "Please enter minimum 3 Characters ")
	private String feature;

	@NotBlank
	@Pattern(regexp = "^[a-zA-Z0-9&.\\-_ ]*$", message = " Please enter characters only")
	@Size(min = 3, message = "Please enter minimum 3 Characters ")
	private String module;

}
