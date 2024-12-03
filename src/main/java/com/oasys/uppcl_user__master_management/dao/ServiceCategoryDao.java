package com.oasys.uppcl_user__master_management.dao;

import java.util.UUID;

import com.oasys.config.BaseDTO;
import com.oasys.uppcl_user__master_management.dto.ServiceCategoryDTO;
import com.oasys.uppcl_user__master_management.entity.ServiceCategoryEntity;
import com.oasys.uppcl_user__master_management.entity.ServiceProviderEntity;


public interface ServiceCategoryDao {
	BaseDTO create(ServiceCategoryDTO dto);

	BaseDTO delete(UUID id);

	BaseDTO update(UUID id, ServiceCategoryDTO dto);

	BaseDTO getAllActive();

	BaseDTO getById(UUID id);

	BaseDTO getByName(String name);

	ServiceProviderEntity getChargesByName(String name);

	ServiceCategoryEntity getByNameIgnoreCase(String name);

	ServiceProviderEntity getAadhaarChargesByName(String name, UUID id);

	ServiceCategoryEntity getMinMaxAmountByName(String name);

	BaseDTO getAllOrderByModifiedDate();
}
