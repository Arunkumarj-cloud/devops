package com.oasys.uppcl_user__master_management.dto;

import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceTypeDTO {

	private UUID id;

	@NotBlank
	@Pattern(regexp = "^[a-zA-Z 0-9]*$", message = "Please Enter AlphaNumeric Only")
	@Size(min = 3, message = "Please Enter Minimum 3 Characters")
	private String serviceType;

	@NotNull
	private Boolean status;

	List<ServiceDTO> serviceList;

}
