package com.oasys.uppcl_user__master_management.dao;

import java.util.UUID;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.StatusMasterDTO;



public interface StatusMasterDao {
    BaseDTO getById(UUID id);
	
	BaseDTO create(StatusMasterDTO statusMasterDTO);

	BaseDTO getAll();

	BaseDTO getLazyList(PaginationRequestDTO requestData);

	BaseDTO update(UUID id, StatusMasterDTO statusMasterDTO);

	BaseDTO getAllActive();

}
