package com.oasys.uppcl_user__master_management.dao;

import java.util.UUID;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.BankPassbookMasterDTO;

public interface BankPassbookMasterDao {
	
	public BaseDTO getAll();

	public BaseDTO create(BankPassbookMasterDTO bankPassbookMasterDTO);

	public BaseDTO update(UUID id, BankPassbookMasterDTO bankPassbookMasterDTO);

	public BaseDTO getById(UUID id);

	public BaseDTO delete(UUID id);
	
	public BaseDTO getAllActive();

	public BaseDTO getAllLazy(PaginationRequestDTO pageData);

	BaseDTO softDelete(UUID id);

}
