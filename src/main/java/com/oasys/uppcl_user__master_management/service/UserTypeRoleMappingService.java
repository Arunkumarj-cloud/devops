package com.oasys.uppcl_user__master_management.service;

import java.util.UUID;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.UserTypeRoleMappingDTO;


public interface UserTypeRoleMappingService {
	
	BaseDTO create(UserTypeRoleMappingDTO userTypeRoleMappingDTO);

	BaseDTO getById(UUID id);

	BaseDTO getAll();

	BaseDTO getAllActive();

	BaseDTO getLazyList(PaginationRequestDTO requestData);

	BaseDTO delete(UUID id);

	BaseDTO update(UUID id,UserTypeRoleMappingDTO userTypeRoleMappingDTO);

	BaseDTO softDelete(UUID id);
	BaseDTO getByUserTypeId(UUID id);

}
