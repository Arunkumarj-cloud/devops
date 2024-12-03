package com.oasys.uppcl_user__master_management.service;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.GenderMasterDTO;

@Service
public interface GenderMasterService {
	public BaseDTO create(GenderMasterDTO genderMasterDTO);

	public BaseDTO update(UUID id, @Valid GenderMasterDTO genderMasterDTO);

	public BaseDTO getAllLazy(PaginationRequestDTO requestData);

	BaseDTO getById(UUID id);

	BaseDTO getAll();

	public BaseDTO getAllActive();

	public BaseDTO softDelete(UUID id);
}
