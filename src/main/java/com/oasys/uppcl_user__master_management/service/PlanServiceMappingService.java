package com.oasys.uppcl_user__master_management.service;

import java.util.UUID;

import com.oasys.config.BaseDTO;
import com.oasys.uppcl_user__master_management.dto.PlanServiceMappingRequestDTO;

public interface PlanServiceMappingService {

	BaseDTO save(PlanServiceMappingRequestDTO requestDTO);
	BaseDTO update(PlanServiceMappingRequestDTO requestDTO);
	BaseDTO getByPlanId(UUID planId);
	BaseDTO update(String planId, String serviceId);
	BaseDTO deleteByPlanIdAndServiceId(UUID planId, UUID serviceId);
	BaseDTO getAll();
}
