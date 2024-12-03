package com.oasys.uppcl_user__master_management.service;

import java.util.List;
import java.util.UUID;

import com.oasys.config.BaseDTO;
import com.oasys.uppcl_user__master_management.dto.PrivilegeChildDto;
import com.oasys.uppcl_user__master_management.dto.PrivilegeDto;

public interface PrivilegeService {

	public BaseDTO addPrivilege(PrivilegeDto privileges);
	
	public BaseDTO deletePrivilege(String privileges);
	
	public BaseDTO viewAllPrivilege();
	
	public BaseDTO addChilds(String privilegeName, List<PrivilegeChildDto> privilegeDtos);
	
	public BaseDTO getAllAndAssignPrivilage(UUID roleId);
	public BaseDTO addNewPrivilege(PrivilegeDto privileges);


}
