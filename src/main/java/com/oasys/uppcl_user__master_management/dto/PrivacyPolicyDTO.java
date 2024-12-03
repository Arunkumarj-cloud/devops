package com.oasys.uppcl_user__master_management.dto;

import java.util.Date;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PrivacyPolicyDTO {

	private UUID id;

	private Date createdDate;
	private Date modifiedDate;
	@NotBlank
	@Size(min = 10, message = "Please enter minimum 10 characters")
	private String description;
	
//	@NotBlank
	private String remarks;
	
	@NotNull
	private Boolean status;

}
