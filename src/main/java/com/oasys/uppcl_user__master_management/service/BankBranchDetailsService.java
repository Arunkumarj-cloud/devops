package com.oasys.uppcl_user__master_management.service;

import java.util.List;
import java.util.UUID;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.BankBranchDetailsDTO;
import com.oasys.uppcl_user__master_management.entity.BankBranchDetailsEntity;

public interface BankBranchDetailsService {

	BaseDTO softDelete(UUID id);

	BaseDTO getAllActive();

	BaseDTO delete(UUID id);

	BaseDTO getById(UUID id);

	BaseDTO update(UUID id, BankBranchDetailsDTO bankBranchDetailsDTO);

	BaseDTO create(BankBranchDetailsDTO bankBranchDetailsDTO);

	BaseDTO getAll();

	BaseDTO getBankBranchDetails(BankBranchDetailsDTO bankBranchDetailsDTO);

	BaseDTO getLazyListWithIfscCode(PaginationRequestDTO pageData);

	BaseDTO getLazyListWithBranchName(PaginationRequestDTO requestData);

	BaseDTO getBranchList(PaginationRequestDTO requestData);

	BaseDTO getBankdetailsByIfscCode(BankBranchDetailsDTO bankBranchDetailsDTO);

	List<BankBranchDetailsEntity> listAll();
}
