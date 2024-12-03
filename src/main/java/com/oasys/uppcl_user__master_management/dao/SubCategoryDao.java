package com.oasys.uppcl_user__master_management.dao;

import java.util.UUID;

import com.oasys.config.BaseDTO;
import com.oasys.uppcl_user__master_management.dto.SearchDTO;
import com.oasys.uppcl_user__master_management.dto.SubCategoryDTO;

public interface SubCategoryDao {

	BaseDTO getAllActive();

	BaseDTO update(UUID id, SubCategoryDTO dto);

	BaseDTO create(SubCategoryDTO dto);

	BaseDTO delete(UUID id);

	BaseDTO getByServiceCategoryId(UUID id);

	BaseDTO getById(UUID id);

	BaseDTO getAllActiveWithSearch(SearchDTO searchDTO);

	BaseDTO getByServiceCategoryByName(String name);

	BaseDTO getByName(String name);
	
	BaseDTO getByServiceAndSubcategory(String serviceName,String categoryName);

	BaseDTO getAllOrderByModifiedDate();

}
