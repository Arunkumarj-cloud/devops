package com.oasys.uppcl_user__master_management.dao;

import java.util.UUID;

import com.oasys.config.BaseDTO;
import com.oasys.uppcl_user__master_management.dto.SearchDTO;
import com.oasys.uppcl_user__master_management.dto.ServiceProviderDTO;

public interface ServiceProviderDao {
	
	BaseDTO create(ServiceProviderDTO dto);

	BaseDTO delete(UUID id);

	BaseDTO update(UUID id, ServiceProviderDTO dto);

	BaseDTO getAllActive();

	BaseDTO getByServiceCategoryId(UUID id);

	BaseDTO getById(UUID id);

	BaseDTO getAllActiveWithSearch(SearchDTO searchDTO);

	BaseDTO getByName(String name);

	BaseDTO getByNameAndId(String name, UUID id);

	BaseDTO getAllOrderByModifiedDate();

}
