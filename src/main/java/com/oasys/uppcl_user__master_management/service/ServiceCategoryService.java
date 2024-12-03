package com.oasys.uppcl_user__master_management.service;

import java.util.UUID;

import javax.validation.Valid;

import com.oasys.config.BaseDTO;
import com.oasys.uppcl_user__master_management.dto.BaseDTOMinMax;
import com.oasys.uppcl_user__master_management.dto.ServiceCategoryDTO;

public interface ServiceCategoryService {
	BaseDTO create(@Valid ServiceCategoryDTO dto);

	BaseDTO delete(UUID id);

	BaseDTO getAllActive();

	BaseDTO update(UUID id, @Valid ServiceCategoryDTO dto);

	BaseDTO getById(UUID id);

	BaseDTO getByName(String name);

	BaseDTO getChargesByName(String userUUID,String name,Double amount,String subCategory);

	BaseDTO getAdhaarPayCharges(String userUUID, String name, Double amount,String subCategory);

	BaseDTO getFundTransferCharges(String userUUID, String name, Double amount,String subCategory);

	BaseDTOMinMax getMinMaxAmountByName(String name);

	BaseDTO getAllOrderByModifiedDate();

}
