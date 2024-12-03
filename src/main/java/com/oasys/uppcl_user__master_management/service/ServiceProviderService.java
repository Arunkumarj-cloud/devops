package com.oasys.uppcl_user__master_management.service;

import java.util.UUID;

import javax.validation.Valid;

import com.oasys.config.BaseDTO;
import com.oasys.uppcl_user__master_management.dto.SearchDTO;
import com.oasys.uppcl_user__master_management.dto.ServiceProviderDTO;

public interface ServiceProviderService {

	BaseDTO getAllActive();

	BaseDTO update(UUID id, @Valid ServiceProviderDTO dto);

	BaseDTO delete(UUID id);

	BaseDTO create(@Valid ServiceProviderDTO dto);

	BaseDTO getByServiceCategoryId(UUID id);

	BaseDTO getById(UUID id);

	BaseDTO getAllActiveWithSearch(SearchDTO searchDTO);

	BaseDTO getByName(String name);
	
	BaseDTO getByNameAndId(String name,UUID id);

	BaseDTO getAllOrderByModifiedDate();
}
