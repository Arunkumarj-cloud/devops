package com.oasys.uppcl_user__master_management.service;

import java.util.UUID;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.StatusMasterDTO;




public interface StatusMasterService {
	BaseDTO create(StatusMasterDTO statusMasterDTO);

	BaseDTO getById(UUID id);

	BaseDTO getAll();

	BaseDTO getLazyList(PaginationRequestDTO requestData);

	BaseDTO softDelete(UUID id);

	BaseDTO getAllActive();

	BaseDTO update(UUID id, StatusMasterDTO statusMasterDTO);


}
