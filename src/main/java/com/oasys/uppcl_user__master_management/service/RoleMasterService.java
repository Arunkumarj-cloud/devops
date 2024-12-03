package com.oasys.uppcl_user__master_management.service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.PrivilegeChildDto;
import com.oasys.uppcl_user__master_management.dto.PrivilegeDto;
import com.oasys.uppcl_user__master_management.dto.RoleMasterDTO;
import com.oasys.uppcl_user__master_management.entity.PrivilegeEntity;

public interface RoleMasterService {
	BaseDTO create(RoleMasterDTO roleMaster);

	BaseDTO getAll();

	BaseDTO update(UUID id, RoleMasterDTO roleDTO);

	BaseDTO getById(UUID id);

	BaseDTO delete(UUID id);

	BaseDTO getAllActive();

	BaseDTO getLazyList(PaginationRequestDTO requestData);
	
	BaseDTO getByName(String name);

	BaseDTO softDelete(UUID id);
	
	BaseDTO getByUserTypeId(UUID id);
	
	BaseDTO getAllRole();

	BaseDTO getAllRoleList();
	
	BaseDTO getRoleList(UUID id);

	BaseDTO getRoleCreator();

	BaseDTO getRoleCreation(UUID id);
	BaseDTO getActiveUserListForKYCAdmin();
	public BaseDTO mappedPrivilegeToRole(final UUID roleId, final Set<String> privilege);
	public BaseDTO getPrivilegeOfRole(final UUID roleId);
	
	public BaseDTO mappedPrivilegeToDefaultRole(final PrivilegeDto privileges);
	
	BaseDTO getAllRolesInUserRoleList(List<String> userRoleNames);
	
	BaseDTO getAllRolesNotInUserRoleList(List<String> userRoleNames);
	
	BaseDTO getAllRolesByIds(List<UUID> roleIds);

	public BaseDTO mappedAddPrivilegeToDefaultRole(final String privilegeName,PrivilegeEntity privilegeEntity, final List<PrivilegeChildDto> privilegeDtos);


}
