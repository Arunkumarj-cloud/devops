package com.oasys.uppcl_user__master_management.dto;

import java.util.List;
import java.util.UUID;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class WorkflowProjectDetailsDTO {

	private UUID id;

	private UUID wfId;

	private UUID applicationId;

	private String workflowName;

	private Integer noOfLevels;

	private Integer levelNo;

	private List<RoleResponse> roleResponses;
	
	private Integer successLevel;	
	
	private Integer rejectLevel;
	
	private Boolean isActive;

	

}
