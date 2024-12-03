package com.oasys.uppcl_user__master_management.service;

import java.util.UUID;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.UserTypeDTO;


public interface UserTypeService {

	BaseDTO create(UserTypeDTO userTypeDTO);

	BaseDTO getById(UUID id);

	BaseDTO getAll();

	BaseDTO getAllActive();

	BaseDTO getLazyList(PaginationRequestDTO requestData);

	BaseDTO delete(UUID id);

	BaseDTO update(UUID id,UserTypeDTO userTypeDTO);

	BaseDTO softDelete(UUID id);
	
	BaseDTO getByName(String name);
	
	BaseDTO validateUserType(String name);
}
