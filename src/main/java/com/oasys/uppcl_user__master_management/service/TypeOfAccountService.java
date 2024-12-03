package com.oasys.uppcl_user__master_management.service;

import java.util.UUID;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.TypeOfAccountDTO;


public interface TypeOfAccountService {
	
	BaseDTO create(TypeOfAccountDTO typeOfAccountDTO);

	BaseDTO getById(UUID id);

	BaseDTO getAll();
	
	BaseDTO getAllActive();
	
	BaseDTO getLazyList(PaginationRequestDTO requestData);

	BaseDTO delete(UUID id);
	
	BaseDTO softDelete(UUID id);

	BaseDTO update(UUID id,TypeOfAccountDTO typeOfAccountDTO);

}
