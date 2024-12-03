package com.oasys.uppcl_user__master_management.dto;

import java.util.Date;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.oasys.uppcl_user__master_management.entity.StateMasterEntity;

import lombok.Data;

@Data
public class DistrictMasterDTO {
	
private UUID id;
	
	@NotBlank
	@Pattern(regexp = "^[a-zA-Z&.\\-_ ]*$", message = "Please enter Characters only")
	@Size(min=3,message="Please enter minimum 3 Characters")
	private String districtName;
	
	//@NotBlank
	@Pattern(regexp ="^[a-zA-z0-9]{3,5}$", message="District  Code is Numbers/Characters only")
	private String districtCode;
	
	@NotNull
	private StateMasterEntity stateId;
	@NotNull
	private Boolean status;
	private String isDeleted;
	
	private String stateCode;
	
	private UUID createdBy;
	
	private UUID modifiedBy;
	
	private Date createdDate;
	
	private Date modifiedDate;
	private Integer pageNo;
	private Integer PageSize;
	
	private String remarks;

}