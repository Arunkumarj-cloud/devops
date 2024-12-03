package com.oasys.uppcl_user__master_management.service;

import java.util.UUID;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.MaritalStatusDTO;

public interface MaritalStatusService {
	public BaseDTO createMaritalStatus(MaritalStatusDTO maritalStatusDTO);

	public BaseDTO getMaritalStatus();

	public BaseDTO getMaritalStatusById(UUID id);

	public BaseDTO updateMaritalStatus(UUID id, MaritalStatusDTO maritalStatusDTO);

	public BaseDTO softDelete(UUID id);

	public BaseDTO getAllMaritalStatuslazy(PaginationRequestDTO pageData);

	public BaseDTO getAllActive();

	public BaseDTO deleteMarital(UUID id);

}
