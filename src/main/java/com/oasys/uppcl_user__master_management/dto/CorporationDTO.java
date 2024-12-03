package com.oasys.uppcl_user__master_management.dto;

import java.util.Date;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.oasys.uppcl_user__master_management.entity.DistrictMasterEntity;

import lombok.Data;

@Data
public class CorporationDTO {
	
private UUID id;
	
	@NotBlank
	 @Pattern(regexp = "^[a-z A-Z]*$", message = " Please enter Characters only ")
	@Size(min = 3, message = "Please enter minimum 3 Characters ")
    private String corporationName;
	
	@NotBlank
	@Pattern(regexp ="^[a-zA-z0-9]{3,5}$", message="please enter numbers/characters only")
    private String corporationCode;
	
	@NotNull
    private DistrictMasterEntity districtId;
	@NotNull
	private Boolean status;
	private UUID createdBy;
	
	private UUID modifiedBy;
	
	private Date createdDate;
	
	private Date modifiedDate;
	

}
