package com.oasys.uppcl_user__master_management.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.Data;
@Data
public class RoleResponse {
	
	private UUID id;
	
	private String roleName;
	
	private String description;
	
  	//private List<UserTypeRspDTO> userType;
	
	private Boolean status;
	
	public Date createdDate;
	
	public UUID createdBy;
	
	public Date modifiedDate;
	
	public UUID modifiedBy;
	
	public String userRoleName;

}
