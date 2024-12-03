package com.oasys.uppcl_user__master_management.service;

import java.util.UUID;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.WardMasterDTO;

public interface WardMasterService {

	BaseDTO create(WardMasterDTO wardMasterDTO);

	BaseDTO delete(UUID id);

	BaseDTO getById(UUID id);

	BaseDTO update(UUID id, WardMasterDTO wardMasterDTO);

	BaseDTO getAll();

	BaseDTO getAllActive();

	BaseDTO getLazyList(PaginationRequestDTO requestData);

	BaseDTO softDelete(UUID id);

}
