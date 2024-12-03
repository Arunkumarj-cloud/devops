package com.oasys.uppcl_user__master_management.dto;

import java.util.Date;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.oasys.uppcl_user__master_management.entity.DistrictMasterEntity;
import com.oasys.uppcl_user__master_management.entity.StateMasterEntity;

import lombok.Data;

@Data
public class CityMasterDTO {
private UUID id;
	
	@NotBlank
	@Pattern(regexp = "^[a-zA-Z&.\\-_ ]*$", message = "Please enter Characters only")
	@Size(min=3,message="Please enter minimum 3 Characters")
	private String cityName;
	
	@NotNull
	private StateMasterEntity stateId;
	
	@NotNull
	private DistrictMasterEntity districtId;
	
	@NotNull
	private Boolean Status;
	
	private UUID createdBy;
	
	private UUID modifiedBy;
	
	private Date createdDate;
	
	private Date modifiedDate;
	


}
