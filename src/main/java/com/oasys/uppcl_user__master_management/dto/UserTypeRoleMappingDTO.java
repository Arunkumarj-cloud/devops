package com.oasys.uppcl_user__master_management.dto;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import com.oasys.uppcl_user__master_management.entity.RoleMasterEntity;
import com.oasys.uppcl_user__master_management.entity.UserTypeMasterEntity;

import lombok.Data;


@Data
public class UserTypeRoleMappingDTO {
	
	private UUID id;
	
	@NotNull
	private UserTypeMasterEntity userTypeId;
	
	@NotNull
	private RoleMasterEntity roleId;
	
	@NotNull
	private Boolean status;
	
	private String remarks;
	
}
