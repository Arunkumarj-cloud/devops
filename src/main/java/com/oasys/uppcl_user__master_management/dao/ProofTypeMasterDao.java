package com.oasys.uppcl_user__master_management.dao;

import java.util.Optional;
import java.util.UUID;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.entity.ProofTypeMasterEntity;

public interface ProofTypeMasterDao {

	BaseDTO getAll();

	ProofTypeMasterEntity save(ProofTypeMasterEntity proofTypeMasterDTO);

	ProofTypeMasterEntity findByProofTypeName(String proofType);

	BaseDTO delete(UUID id);

	Optional<ProofTypeMasterEntity> getById(UUID id);

	public BaseDTO getAllLazy(PaginationRequestDTO requestData);

	public BaseDTO getAllActive();

	
}
