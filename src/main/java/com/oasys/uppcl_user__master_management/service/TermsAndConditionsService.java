package com.oasys.uppcl_user__master_management.service;

import java.util.UUID;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.TermsAndConditionsDTO;


public interface TermsAndConditionsService {
	
	public BaseDTO getAll();

	public BaseDTO create( TermsAndConditionsDTO termsAndConditionsDTO);

	public BaseDTO delete( UUID id);

	public BaseDTO update( UUID id,  TermsAndConditionsDTO termsAndConditionsDTO);

	public BaseDTO getById( UUID id);

	public BaseDTO getLazyList( PaginationRequestDTO requestData);

	public BaseDTO getAllActive();

	public BaseDTO softDelete(UUID id);

}
