package com.oasys.uppcl_user__master_management.dto;

import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;



import lombok.Data;

@Data
public class UserTypeDTO {

	private UUID id;

	@NotBlank
	@Size(min = 3, message = "Please enter minimum 3 characters")
	@Pattern(regexp = "^[a-zA-Z0-9&.\\-_ ]*$", message = "Please enter numbers/characters only")
	private String userType;

	@NotBlank
	@Size(min = 3, message = "Please enter minimum 3 characters")
	private String description;

	@NotNull
	private Boolean status;
	
	
	

}
