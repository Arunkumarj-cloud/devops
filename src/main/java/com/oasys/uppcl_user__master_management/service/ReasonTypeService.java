package com.oasys.uppcl_user__master_management.service;

import java.util.UUID;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.ReasonTypeDTO;



public interface ReasonTypeService {
	
	
	BaseDTO create(ReasonTypeDTO reasonTypeDTO);

	
	  BaseDTO getById(UUID id);
	  
	  BaseDTO getAll();
	  
	  BaseDTO getAllActive();
	  
	  BaseDTO getLazyList(PaginationRequestDTO requestData);
	  
	  BaseDTO update(UUID id,ReasonTypeDTO reasonTypeDTO);
	  
	  BaseDTO delete(UUID id);
	  
	  BaseDTO softDelete(UUID id);
	 


}
