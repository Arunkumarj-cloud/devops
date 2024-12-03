package com.oasys.uppcl_user__master_management.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.PlanMasterDTO;


@Service
public interface PlanMasterService {

	BaseDTO addPlan(PlanMasterDTO planMasterDTO);

	BaseDTO getAllPlans();

	BaseDTO deletePlan(UUID id);

	BaseDTO softDeletePlan(UUID id);

	BaseDTO updatePlan(UUID id, PlanMasterDTO planMasterDTO);

	BaseDTO getById(UUID id);

	public BaseDTO getLazyList(PaginationRequestDTO requestData);

	BaseDTO getDefaultPlan();

	BaseDTO findbyPlanUUID(String name);
	Boolean isPlanExistById(UUID id);
	BaseDTO getAllActive();
}
