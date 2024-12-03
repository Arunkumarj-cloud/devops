package com.oasys.uppcl_user__master_management.dto;

import java.util.UUID;

import javax.persistence.Column;

import lombok.Data;

@Data
public class PackageHistoryResponseDTO {

	private UUID id;
	private String packageName;
	private Double packageAmount;
	private Double previousAmount;
	private String serviceName;
	private String actionType;
	private String actionDateTime;
	private String remarks;
	private String modifiedByUserId;
	private String modifiedByUsername;
	private Boolean previousStatus;
	private Boolean updatedStatus;
	private String ipAddress;
}
