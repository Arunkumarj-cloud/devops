package com.oasys.uppcl_user__master_management.dto;

import java.util.Date;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.oasys.uppcl_user__master_management.entity.ReasonTypeEntity;

import lombok.Data;

@Data
public class ComplaintReasonDTO {
	
	private UUID id;
	@NotNull 
	private ReasonTypeEntity reasonTypeId;
	
	@NotBlank
	//@Pattern(regexp ="^[a-zA-z0-9]{1,5}$", message="Please enter Numbers/characters only")
	private String reasonCode;

	@NotBlank
	@Size(min = 3, message = "Please enter minimum 3 Characters")
	private String reasonName;
	
	@NotNull 
	private Boolean status;
	
	private Date createdDate;

}
