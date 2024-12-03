package com.oasys.uppcl_user__master_management.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.entity.WardMasterEntity;


public interface WardMasterDao {

	WardMasterEntity save(WardMasterEntity wardMasterEntity);

	WardMasterEntity findByWardName(String wardName);

	BaseDTO delete(UUID id);
	
	Optional<WardMasterEntity> findById(UUID id);
	
	BaseDTO getAll();

	BaseDTO getAllActive();

	BaseDTO getLazyList(PaginationRequestDTO requestData);
	
	List<WardMasterEntity> findByIdNotIn(UUID id);

}
