package com.oasys.uppcl_user__master_management.dao;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.entity.BankBranchMasterEntity;
import com.oasys.uppcl_user__master_management.entity.BankNameMasterEntity;


public interface BankBranchDao  {
	List<BankBranchMasterEntity> getAll();

	List<BankBranchMasterEntity> getAllActive();

	BaseDTO getLazyList(PaginationRequestDTO requestData);

	BaseDTO delete(UUID id);

	BaseDTO softDelete(UUID id);

	Optional<BankBranchMasterEntity> findById(UUID id);

	Optional<BankBranchMasterEntity> findByIfscCode(String ifscCode);

	List<BankBranchMasterEntity> getByExcepted(UUID id);

	BankBranchMasterEntity save(BankBranchMasterEntity entity);

	List<BankBranchMasterEntity> getByBankNameId(BankNameMasterEntity id);


}
