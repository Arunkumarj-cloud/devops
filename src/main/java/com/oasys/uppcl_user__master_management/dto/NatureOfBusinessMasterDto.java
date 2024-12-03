package com.oasys.uppcl_user__master_management.dto;

import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class NatureOfBusinessMasterDto {

	

		private UUID id;

		@NotBlank
		@Pattern(regexp = "^[a-zA-Z 0-9&.\\-_ ]*$", message = "Please Enter AlphaNumeric Only")
		@Size(min = 3, message = "Please Enter Minimum 3 Characters")
		private String name;

		@NotBlank
		private String remarks;
		@NotNull
		private Boolean status;
		
		@NotBlank
		@Pattern(regexp = "^[0-9]{4}$", message = "Please Enter four digit Number Only" )
		 private String mccCode;
		

}
