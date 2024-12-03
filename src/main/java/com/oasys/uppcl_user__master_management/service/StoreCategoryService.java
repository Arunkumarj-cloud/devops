package com.oasys.uppcl_user__master_management.service;

import java.util.UUID;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.StoreCategoryDTO;


public interface StoreCategoryService {
	
	public BaseDTO create(StoreCategoryDTO storeCategoryDTO);

	public BaseDTO getById(UUID id);

	public BaseDTO getAll();
	
	public BaseDTO getAllActive();
	
	public BaseDTO getLazyList(PaginationRequestDTO requestData);

	public BaseDTO update(UUID id, StoreCategoryDTO storeCategoryDTO);
	
	public BaseDTO delete(UUID id);

	public BaseDTO softDelete(UUID id);

}
