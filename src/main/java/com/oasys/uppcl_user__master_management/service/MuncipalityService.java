package com.oasys.uppcl_user__master_management.service;

import java.util.UUID;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.MuncipalityDTO;

public interface MuncipalityService {
	public BaseDTO createMuncipality(MuncipalityDTO muncipalityDTO);

	public BaseDTO getMuncipality();

	public BaseDTO getMuncipalityById(UUID id);

	public BaseDTO updateMuncipality(UUID id, MuncipalityDTO muncipalityDTO);

	public BaseDTO softDelete(UUID id);

	public BaseDTO getLazyList(PaginationRequestDTO dto);
	
	public BaseDTO getDistrictById(UUID id);

	public BaseDTO deleteMunicipality(UUID id);
}
