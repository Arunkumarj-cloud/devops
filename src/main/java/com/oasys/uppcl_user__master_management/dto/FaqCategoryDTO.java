package com.oasys.uppcl_user__master_management.dto;

import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class FaqCategoryDTO {
	 UUID id;
	    
	    @NotBlank
	    @Pattern(regexp = "^[a-zA-Z0-9&.\\-_ ]*$", message = "Please Enter AlphaNumeric only")
	    @Size(min = 3, message = "Please Enter Minimum 3 Characters")
	    String categoryName;
	    
//		@NotBlank
//		@Size(min = 3, message = "Please Enter Minimum 3 Characters ")
		String categoryDescription;
		
	    @NotNull
		Boolean status;
	    
	    String remarks;

}
