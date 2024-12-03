package com.oasys.uppcl_user__master_management.service;

import java.util.UUID;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.ServiceTypeDTO;

public interface ServiceTypeService {
	
	BaseDTO create(ServiceTypeDTO serviceTypeDTO);

	BaseDTO getById(UUID id);

	BaseDTO getAll();
	
	BaseDTO getAllActive();
	
	BaseDTO getLazyList(PaginationRequestDTO requestData);

	BaseDTO update(UUID id,ServiceTypeDTO serviceTypeDTO);
	
	BaseDTO delete(UUID id);
	
	BaseDTO softDelete(UUID id);
	
	BaseDTO fetchServiceTypeWithServiceList();

}
