package com.oasys.uppcl_user__master_management.dto;

import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.oasys.uppcl_user__master_management.entity.DistrictMasterEntity;

import lombok.Data;

@Data
public class TalukMasterDTO {
	
	private UUID id;
	
	  @NotBlank
	  @Pattern(regexp = "^[a-zA-Z &.\\-_]*$", message = "Please Enter Characters Only")
	  private String talukName;
	  
	  private String talukCode;
	  
	  @NotNull
	  private Boolean status;
	  
	  @NotNull
	  private DistrictMasterEntity districtId ;

}
