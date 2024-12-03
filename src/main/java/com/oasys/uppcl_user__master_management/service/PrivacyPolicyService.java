package com.oasys.uppcl_user__master_management.service;

import java.util.UUID;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.PrivacyPolicyDTO;


public interface PrivacyPolicyService {
	
	public BaseDTO create( PrivacyPolicyDTO privacyPolicyDTO);

	public BaseDTO delete( UUID id);

	public BaseDTO update( UUID id,  PrivacyPolicyDTO privacyPolicyDTO);

	public BaseDTO getById( UUID id);
	
	public BaseDTO getAll();

	public BaseDTO getLazyList( PaginationRequestDTO requestData);

	public BaseDTO getAllActive();

	 public BaseDTO softDelete(UUID id);

}
