package com.oasys.uppcl_user__master_management.dao;

import java.util.UUID;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.MerchantTypeMasterDTO;

public interface MerchantTypeMasterDao {
	public BaseDTO getAll();

	public BaseDTO create( MerchantTypeMasterDTO merchantTypeDTO);
	
	public BaseDTO delete( UUID id);
	
	public BaseDTO update( UUID id,  MerchantTypeMasterDTO merchantTypeDTO);
	
	public BaseDTO getById( UUID id);
	
	public BaseDTO getAllActive();
	
	public BaseDTO getLazyList( PaginationRequestDTO requestData);
}
