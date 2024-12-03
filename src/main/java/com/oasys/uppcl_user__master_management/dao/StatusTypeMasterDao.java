package com.oasys.uppcl_user__master_management.dao;

import java.util.UUID;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.StatusTypeMasterDTO;


public interface StatusTypeMasterDao {
	
    BaseDTO create(StatusTypeMasterDTO statusTypeMasterDTO);
	
	BaseDTO getById(UUID id);

	BaseDTO getAll();

	BaseDTO getLazyList(PaginationRequestDTO requestData);

	BaseDTO getAllActive();

	BaseDTO update(UUID id, StatusTypeMasterDTO statusTypeMasterDTO);

}
