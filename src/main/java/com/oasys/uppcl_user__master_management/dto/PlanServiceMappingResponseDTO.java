package com.oasys.uppcl_user__master_management.dto;

import java.util.List;
import java.util.UUID;

import lombok.Data;
@Data
public class PlanServiceMappingResponseDTO {
	private UUID planId;
	private String planName;
	private List<ServiceMasterResponseDTO> serviceMasterResponseList;
}
