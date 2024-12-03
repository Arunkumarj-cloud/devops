package com.oasys.uppcl_user__master_management.service;

import java.util.UUID;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.ReligionMasterDTO;

public interface ReligionMasterService {

	public BaseDTO getAll();
	
	public BaseDTO create(ReligionMasterDTO religionMasterDTO) ;
	
	public BaseDTO update(UUID id,ReligionMasterDTO religionMasterDTO);

	public BaseDTO getById( UUID id);
	
	public BaseDTO delete( UUID id);

	public BaseDTO getAllLazy(PaginationRequestDTO requestData);

	public BaseDTO getAllActive();

	public BaseDTO softDelete(UUID id);
}
