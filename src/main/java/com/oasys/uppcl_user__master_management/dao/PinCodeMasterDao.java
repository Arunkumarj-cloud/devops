package com.oasys.uppcl_user__master_management.dao;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.entity.PinCodeMasterEntity;


public interface PinCodeMasterDao  {

	PinCodeMasterEntity findByPinCode(String pinCode);

	PinCodeMasterEntity save(PinCodeMasterEntity regEntity);

	BaseDTO getLazyList(PaginationRequestDTO pageData);
	
	

}
