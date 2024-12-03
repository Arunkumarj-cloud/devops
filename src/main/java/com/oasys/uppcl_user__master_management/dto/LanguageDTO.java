package com.oasys.uppcl_user__master_management.dto;

import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;
@Data
public class LanguageDTO {
private UUID id;
	
	@NotBlank
	@Pattern(regexp = "^[a-z A-Z]*$", message = "Please enter Characters only")
    @Size (min = 3, message = "Please enter minimum 3 Characters")
	private String languageName;
	
	@NotBlank
	@Pattern(regexp ="^[a-zA-z0-9]{3,5}$", message=" Code should be AlphaNumeric only")
	private String code;
	
	@NotNull
	private Boolean status;
}
