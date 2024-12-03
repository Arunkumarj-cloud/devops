package com.oasys.uppcl_user__master_management.service;

import java.util.UUID;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.BankTypeMasterDTO;

public interface BankTypeMasterService {

	public BaseDTO getAll();
	
	public BaseDTO create(BankTypeMasterDTO bankTypeDto);
	
	public BaseDTO delete(UUID id);
	
	public BaseDTO update(UUID id, BankTypeMasterDTO bankTypeDto);
	
	public BaseDTO getById(UUID id);
	
	public BaseDTO getAllActive();

	public BaseDTO getAllLazy(PaginationRequestDTO pageData);

	public BaseDTO softDelete(UUID id);
}
