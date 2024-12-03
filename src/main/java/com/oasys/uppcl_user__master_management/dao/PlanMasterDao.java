package com.oasys.uppcl_user__master_management.dao;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.google.common.base.Optional;
import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.PlanMasterDTO;


@Service
public interface PlanMasterDao {

	BaseDTO addPlan(PlanMasterDTO planMasterDTO);

	BaseDTO getAllPlans();

	BaseDTO deletePlan(UUID id);

	BaseDTO softDeletePlan(UUID id);

	BaseDTO updatePlan(UUID id, PlanMasterDTO planMasterDTO);

	BaseDTO getById(UUID id);
	
	public BaseDTO getLazyList( PaginationRequestDTO requestData);
	
	boolean validateIsDefaultorNot(UUID id);
}
