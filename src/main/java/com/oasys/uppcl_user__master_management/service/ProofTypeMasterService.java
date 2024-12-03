package com.oasys.uppcl_user__master_management.service;

import java.util.List;
import java.util.UUID;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.ProofTypeMasterDTO;


public interface ProofTypeMasterService {

	public BaseDTO getAll();

	public BaseDTO create( ProofTypeMasterDTO proofTypeMasterDTO);

	public BaseDTO delete(UUID id);

	public BaseDTO update(UUID id,ProofTypeMasterDTO proofTypeMasterDTO);

	public BaseDTO getById(UUID id);

	public BaseDTO getLazyList(PaginationRequestDTO requestData);

	public BaseDTO getAllActive();

	public BaseDTO softDelete(UUID id);

   BaseDTO findByProofTypeNames(List<String> proofTypeNames);


}
