package com.oasys.uppcl_user__master_management.service;

import java.util.UUID;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.ProofMasterDTO;

public interface ProofMasterService {

	public BaseDTO getAll();

	public BaseDTO create( ProofMasterDTO proofDTO);

	public BaseDTO delete( UUID id);

	public BaseDTO update( UUID id,  ProofMasterDTO proofDTO);

	public BaseDTO getById( UUID id);

	public BaseDTO getLazyList( PaginationRequestDTO requestData);

	public BaseDTO getAllActive();
	
	BaseDTO getByProofType(UUID id);

    public BaseDTO softDelete(UUID id);

	public BaseDTO getProofNameById(UUID id);


}
