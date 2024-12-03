package com.oasys.uppcl_user__master_management.dto;


import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;



import lombok.Data;

@Data
public class PinCodeMasterDTO {
	private UUID id;
	
	//@NotBlank
	@Size(min=3,message="Please enter minimum 3 Characters")
	private String divisionName;
	
	
	@NotNull
//	@NumberLength(message = "Invalid pincode",max = 6,min = 6)
	private String pinCode ;
//	@NotBlank
	@Size (min = 3, message = "Please enter minimum 3 Characters")
    private String remarks;
	@NotNull
	private UUID districtUUId;
	
	@NotNull
	private Boolean status;
	
	private String isDeleted;
	
	private Integer pageNo;
	
	private Integer PageSize;
	
	private UUID stateId;
}
