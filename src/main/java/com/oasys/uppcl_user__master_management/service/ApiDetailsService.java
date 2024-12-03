package com.oasys.uppcl_user__master_management.service;

import java.util.UUID;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.ApiDetailsDTO;

public interface ApiDetailsService {
	
	BaseDTO save(ApiDetailsDTO dto);

	
	  BaseDTO retrieve_all(PaginationRequestDTO dto);
	  
	  BaseDTO retrieve(UUID id);
	  
	  BaseDTO update(UUID id, ApiDetailsDTO dto);
	  
	  BaseDTO softDelete(UUID id);
	  
	  BaseDTO delete(UUID id);
	  
	  BaseDTO getAllActive();
	  
	  BaseDTO getAll();
	 

}
