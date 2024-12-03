package com.oasys.uppcl_user__master_management.dao;

import java.util.UUID;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.BankNameDTO;

public interface BankNameDao {
	BaseDTO create(BankNameDTO bankNameDTO);

	BaseDTO getById(UUID id);

	BaseDTO getAll();
	
	BaseDTO getAllActive();
	
	BaseDTO getLazyList(PaginationRequestDTO requestData);

	BaseDTO update(UUID id,BankNameDTO bankNameDTO);
	
	BaseDTO delete(UUID id);
	
	BaseDTO softDelete(UUID id);
	
	//BaseDTO getByBanktypeId(UUID id);
	
//	BaseDTO userSearchFilter(BankNameDTO bankNameDTO);

}
