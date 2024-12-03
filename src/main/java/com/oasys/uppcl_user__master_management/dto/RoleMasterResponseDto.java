package com.oasys.uppcl_user__master_management.dto;

import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class RoleMasterResponseDto {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9129175042778865798L;


	private UUID id;
	

	private String roleName;
	

	private String description;
	
	private String userType;
	
	
	private String userRoleName;
	
	private Boolean status;

	private List<String> permissions;
	

}
