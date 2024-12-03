package com.oasys.uppcl_user__master_management.dao;

import java.util.UUID;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.UserTypeDTO;
import com.oasys.uppcl_user__master_management.entity.UserTypeMasterEntity;

public interface UserTypeDao {
	
	BaseDTO create(UserTypeDTO userTypeDTO);

	BaseDTO getById(UUID id);

	BaseDTO getAll();

	BaseDTO getAllActive();

	BaseDTO getLazyList(PaginationRequestDTO requestData);

	BaseDTO delete(UUID id);

	BaseDTO update(UUID id,UserTypeDTO userTypeDTO);

	BaseDTO softDelete(UUID id);
	
	UserTypeMasterEntity getByName(String name);

}
