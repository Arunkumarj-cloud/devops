package com.oasys.uppcl_user__master_management.dao;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.FieldMasterDTO;
@Service
public interface FieldMasterDao {

	BaseDTO create(FieldMasterDTO fieldMasterDTO);

	BaseDTO getById(UUID id);

	BaseDTO getAll();

	BaseDTO getAllActive();

	BaseDTO softDelete(UUID id);

	BaseDTO lazyList(PaginationRequestDTO pageData);

	BaseDTO update(UUID id, FieldMasterDTO fieldMasterDTO);

}