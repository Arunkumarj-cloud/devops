package com.oasys.uppcl_user__master_management.service;

import java.util.UUID;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.BankNameDTO;

public interface BankNameService {
	BaseDTO create(BankNameDTO bankNameDTO);

	BaseDTO getById(UUID id);

	BaseDTO getAll();

	BaseDTO getAllActive();

	BaseDTO getLazyList(PaginationRequestDTO requestData);

	BaseDTO delete(UUID id);

	BaseDTO update(UUID id,BankNameDTO bankNameDTO);

	BaseDTO softDelete(UUID id);
	
	//BaseDTO getByBanktypeId(UUID id);

}
