package com.oasys.uppcl_user__master_management.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oasys.clients.userservice.utils.CommonUtil;
import com.oasys.uppcl_user__master_management.dto.RoleResponse;
import com.oasys.uppcl_user__master_management.entity.RoleMasterEntity;

@Component
public class RoleResponseMapper {

	@Autowired
	private CommonUtil commonUtil;

	public RoleResponse convertEntityToResponseDTO(RoleMasterEntity entity) {
		RoleResponse responseDTO = commonUtil.modalMap(entity, RoleResponse.class);

		return responseDTO;
	}
}
