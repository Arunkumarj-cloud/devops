package com.oasys.uppcl_user__master_management.dto;

import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.oasys.uppcl_user__master_management.entity.ServiceTypeMasterEntity;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceDTO {

	private UUID id;

	@NotBlank
	@Pattern(regexp = "^[a-zA-Z0-9 ]*$", message = "Please Enter AlphaNumeric Only")
	@Size(min = 3, message = "Please Enter Minimum 3 Characters")
	private String serviceName;

	@NotBlank
	@Size(min = 3, message = "Please Enter Minimum 3 Characters")
	private String baseURL;

	@NotBlank
	@Pattern(regexp = "^[a-zA-Z 0-9]*$", message = "Please Enter AlphaNumeric Only")
	@Size(min = 3, message = "Please Enter Minimum 3 Characters/numbers")
	private String serviceNumber;
	
	private String serviceMode;

	@NotNull
	private ServiceTypeMasterEntity serviceTypeId;

	@NotNull
	private Boolean status;
}
