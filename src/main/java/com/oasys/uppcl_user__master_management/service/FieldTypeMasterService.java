package com.oasys.uppcl_user__master_management.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.FieldTypeMasterDTO;

@Service
public interface FieldTypeMasterService {
	BaseDTO getById(UUID id);

	BaseDTO create(FieldTypeMasterDTO fieldTypeMasterDTO);

	BaseDTO getAll();

	BaseDTO getLazyList(PaginationRequestDTO requestData);

	BaseDTO update(UUID id, FieldTypeMasterDTO fieldTypeMasterDTO);

	BaseDTO getAllActive();

	BaseDTO softDelete(UUID id);
}
