package com.oasys.uppcl_user__master_management.dao;

import java.util.UUID;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.ProofTypeProofMappingDTO;

public interface ProofTypeProofMappingDao {

	BaseDTO create(ProofTypeProofMappingDTO proofTypeProofMappingDTO);

	BaseDTO update(UUID id, ProofTypeProofMappingDTO proofTypeProofMappingDTO);

	BaseDTO getById(UUID id);

	BaseDTO getAll();

	BaseDTO getAllActive();

	BaseDTO getLazyList(PaginationRequestDTO requestData);

	BaseDTO delete(UUID id);

	BaseDTO softDelete(UUID id);

	BaseDTO getByProofTypeId(UUID id);

}
