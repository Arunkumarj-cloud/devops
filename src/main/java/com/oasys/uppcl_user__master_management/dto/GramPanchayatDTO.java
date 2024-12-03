package com.oasys.uppcl_user__master_management.dto;

import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.oasys.uppcl_user__master_management.entity.BlockMasterEntity;

import lombok.Data;

@Data
public class GramPanchayatDTO {
	
	
    private UUID id;
	
	@NotBlank
	@Pattern(regexp = "^[a-z A-Z]*$", message = "Please enter minimum 3 Characters")
	@Size (min = 3, message = "Please enter minimum 3 Characters")
	private String panchayatName;
	
	@NotBlank
	@Pattern(regexp ="^[a-zA-z0-9]{3,5}$", message="Panchayat Code should be AlphaNumeric only")
	private String panchayatCode;
	
	@NotNull
	private Boolean status;
	@NotNull
	private BlockMasterEntity blockId;
	/*@NotNull  @NotEmpty
	private String panchayatType;
*/

}
