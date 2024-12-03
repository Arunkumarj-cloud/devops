package com.oasys.uppcl_user__master_management.service;

import java.util.UUID;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.NatureOfBusinessMasterDto;
import com.oasys.uppcl_user__master_management.dto.NatureOfBusinessUpdateDTO;

public interface NatureOfBusinessService {
	public BaseDTO createNatureOfBusiness(NatureOfBusinessMasterDto  natureOfBusinessDTO);

	public BaseDTO getNatureOfBusiness();
//
	public BaseDTO getNatureOfBusinessById(UUID id);
//
	public BaseDTO deleteNature(UUID id);
//
	public BaseDTO updateNatureOfBusiness(UUID id, NatureOfBusinessUpdateDTO  natureOfBusinessDTO);
//
	public BaseDTO getAllNatureOfBusinesslazy(PaginationRequestDTO pageData);
//
public BaseDTO getAllActive();
//
	BaseDTO softDelete(UUID id);

}
