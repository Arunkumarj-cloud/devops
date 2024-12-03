package com.oasys.uppcl_user__master_management.service;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import com.oasys.config.BaseDTO;
import com.oasys.uppcl_user__master_management.dto.SearchDTO;
import com.oasys.uppcl_user__master_management.dto.SubCategoryDTO;

public interface SubCategoryService {

	BaseDTO getAllActive();

	BaseDTO create(@Valid SubCategoryDTO dto);

	BaseDTO delete(UUID id);

	BaseDTO update(UUID id, @Valid SubCategoryDTO dto);

	BaseDTO getByServiceCategoryId(UUID id);

	BaseDTO getById(UUID id);

	BaseDTO getAllActiveWithSearch(SearchDTO searchDTO);

	BaseDTO getByServiceCategoryByName(String name);
	BaseDTO getByIds(List<UUID> id);

	BaseDTO getByName(String name);
	
	BaseDTO getByServiceAndSubcategory(String serviceName,String categoryName);

	BaseDTO getAllOrderByModifiedDate();

}

