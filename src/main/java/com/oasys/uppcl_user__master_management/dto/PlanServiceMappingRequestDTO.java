package com.oasys.uppcl_user__master_management.dto;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import lombok.Data;
@Data
public class PlanServiceMappingRequestDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private UUID planId;
	
	private List<UUID> serviceIds;

}