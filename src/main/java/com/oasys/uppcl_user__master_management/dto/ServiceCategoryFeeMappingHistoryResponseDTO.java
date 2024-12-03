package com.oasys.uppcl_user__master_management.dto;

import java.io.Serializable;
import java.util.UUID;

import lombok.Data;

@Data
public class ServiceCategoryFeeMappingHistoryResponseDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String serviceCategoryName;
	private UUID serviceCategoryId;
	private Double previousAmount;
	private Double updatedAmount;
	private String actionDateTime;
	private String modifiedByUsername;
	private String modifiedByUserId;
	private String actionType;
	private String remarks;
	private String ipAddress;
}
