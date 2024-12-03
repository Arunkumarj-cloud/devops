package com.oasys.uppcl_user__master_management.dao;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.EducationDTO;
@Service
public interface EducationDao {
	BaseDTO create(EducationDTO educationDTO);

	BaseDTO getAllActive();

	BaseDTO update(UUID id, EducationDTO educationDTO);

	BaseDTO getLazyList(PaginationRequestDTO requestData);

	BaseDTO getById(UUID id);

	BaseDTO getAll();

}


