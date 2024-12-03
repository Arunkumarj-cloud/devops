package com.oasys.uppcl_user__master_management.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.StateMasterDTO;
import com.oasys.uppcl_user__master_management.entity.StateMasterEntity;

public interface StateMasterDao {
	
	StateMasterEntity save(StateMasterEntity stateMasterEntity);

	Optional<StateMasterEntity> getState(UUID id);

	BaseDTO getAllState(PaginationRequestDTO dto);

	BaseDTO deleteState(StateMasterDTO dto);

	BaseDTO getActiveStateList();
	
	BaseDTO getAll();
	
	StateMasterEntity findByStateCode(String stateCode);
	
	List<StateMasterEntity> findByIdNotIn(UUID id);

}
