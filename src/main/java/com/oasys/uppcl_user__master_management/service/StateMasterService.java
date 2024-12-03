package com.oasys.uppcl_user__master_management.service;

import java.util.UUID;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.StateMasterDTO;

public interface StateMasterService {
	
	BaseDTO saveState(StateMasterDTO dto);

	BaseDTO updateState(UUID id,StateMasterDTO dto);

	BaseDTO getState(UUID id);

	BaseDTO getAllState(PaginationRequestDTO dto);

	BaseDTO deleteState(StateMasterDTO dto);

	BaseDTO getActiveStateList();
	
	BaseDTO getAll();

	BaseDTO softDelete(UUID id);

	BaseDTO getstateName(String stateName);


}
