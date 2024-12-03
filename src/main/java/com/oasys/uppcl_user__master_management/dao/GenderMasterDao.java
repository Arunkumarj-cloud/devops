package com.oasys.uppcl_user__master_management.dao;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.GenderMasterDTO;
@Service

public interface GenderMasterDao {
	BaseDTO create(GenderMasterDTO genderMasterDTO);

	BaseDTO update(UUID id, GenderMasterDTO genderMasterDTO);

	BaseDTO getById(UUID id);

	BaseDTO getAll();

	BaseDTO getLazyList(PaginationRequestDTO requestData);

	BaseDTO getAllActive();

}
