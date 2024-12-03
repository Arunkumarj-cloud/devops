package com.oasys.uppcl_user__master_management.dto;

import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class RoleMasterDTO {
private UUID id;
	
	@NotBlank
	@Pattern(regexp = "^[a-zA-Z ]*$", message = "Please enter characters")
	@Size(min = 3, message = " Please enter minimum 3 Characters")
	private String roleName;
	
	@NotBlank
	@Size(min = 3, message = "Please enter minimum 3 Characters")
	private String description;
	
	@NotNull
	private Boolean status;
	
	private String userRoleName;
	
	private  List<UserTypeRspDTO> userType;

}
