package com.oasys.uppcl_user__master_management.dao;

import java.util.UUID;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.RoleMasterDTO;

public interface RoleMasterDao {
	
	BaseDTO create(RoleMasterDTO roleMaster);

	BaseDTO getAll();

	BaseDTO update(UUID id, RoleMasterDTO roleDTO);

	BaseDTO getById(UUID id);

	BaseDTO delete(UUID id);
	
    BaseDTO getAllActive();
	
    BaseDTO getLazyList(PaginationRequestDTO requestData);
    
    BaseDTO getByName(String name);

	BaseDTO getByUserTypeId(UUID id);
	
	BaseDTO getAllRole();

	BaseDTO getAllRoleList();
	
	BaseDTO getRoleList(UUID id);

	BaseDTO getRoleCreator();

	BaseDTO getRoleCreation(UUID id);
	
	BaseDTO getActiveUserListForKYCAdmin();
	

}
