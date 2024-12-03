package com.oasys.uppcl_user__master_management.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class CommissionHierarchyResponseDTO {

private static final long serialVersionUID = 1L;
	
	private UUID id;
	private String name;
	private Boolean status;
	private String model;
	private String createdDate;
	private UUID createdBy;
	private String modifiedDate;
	private String createdByUsername;
	private String createdByUserId;
}
