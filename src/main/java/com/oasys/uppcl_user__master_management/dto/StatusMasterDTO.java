package com.oasys.uppcl_user__master_management.dto;

import java.util.Date;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;


@Data
public class StatusMasterDTO {
     
	private UUID id;
	
	@NotBlank
	private String statusName;

	@NotNull
	private Boolean status;
	
	private Date createdDate;

}
