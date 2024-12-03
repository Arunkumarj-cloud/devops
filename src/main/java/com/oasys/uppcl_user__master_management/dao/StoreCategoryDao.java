package com.oasys.uppcl_user__master_management.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.entity.StoreCategoryEntity;


public interface StoreCategoryDao {
	
	StoreCategoryEntity save(StoreCategoryEntity storeCategory);

	BaseDTO getById(UUID id);

	BaseDTO getAll();

	BaseDTO getAllActive();

	BaseDTO getLazyList(PaginationRequestDTO requestData);

	BaseDTO delete(UUID id);

	Optional<StoreCategoryEntity> findById(UUID id);
	
	List<StoreCategoryEntity> findByIdNotIn(UUID id);
	
	StoreCategoryEntity findByName(String name);

}
