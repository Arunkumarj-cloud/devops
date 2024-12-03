package com.oasys.uppcl_user__master_management.dao;

import java.util.List;
import java.util.UUID;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.BankBranchDetailsDTO;
import com.oasys.uppcl_user__master_management.entity.BankBranchDetailsEntity;

public interface BankBranchDetailsDao {
	
	BaseDTO softDelete(UUID id);

	BaseDTO delete(UUID id);

	BaseDTO getLazyList(PaginationRequestDTO pageData);

	BaseDTO getAllActive();

	BaseDTO getById(UUID id);

	BaseDTO update(UUID id, BankBranchDetailsDTO bankBranchDetailsDTO);

	BaseDTO create(BankBranchDetailsDTO bankBranchDetailsDTO);

	//BaseDTO getAll();
	public List<BankBranchDetailsEntity> getAll();

	//BaseDTO getAllLazy(PaginationRequestDTO pageData);

	BaseDTO getBankBranchDetails(String ifscCode);

	BaseDTO getAllLazyWithIfscCode(PaginationRequestDTO pageData);

	BaseDTO getAllLazyWithBranchName(PaginationRequestDTO pageData);

	BaseDTO getBranchList(PaginationRequestDTO pageData);

	//BaseDTO getByBankNameId(UUID id);

}
