package com.oasys.uppcl_user__master_management.mapper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oasys.clients.userservice.utils.CommonUtil;
import com.oasys.config.Constants;
import com.oasys.uppcl_user__master_management.dto.CommissionHierarchyResponseDTO;
import com.oasys.uppcl_user__master_management.dto.ResponseContent;
import com.oasys.uppcl_user__master_management.entity.CommissionHierarchyEntity;

@Component
public class CommissionHierarchyMapper {
	
	@Autowired
	private CommonUtil commonUtil;

	public CommissionHierarchyResponseDTO convertEntityToResponseDTO(CommissionHierarchyEntity entity) {
		CommissionHierarchyResponseDTO responseDTO = commonUtil.modalMap(entity, CommissionHierarchyResponseDTO.class);
		if (Objects.nonNull(entity.getCreatedDate()) || Objects.nonNull(entity.getModifiedDate())) {
			DateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
			responseDTO.setModifiedDate(dateFormat.format(entity.getModifiedDate()));
			responseDTO.setCreatedDate(dateFormat.format(entity.getCreatedDate()));
		}
		if (Objects.nonNull(entity.getCreatedBy())) {
			ResponseContent response = commonUtil.getUserDetailsById(entity.getCreatedBy());
			if (Objects.nonNull(response)) {
				responseDTO.setCreatedByUsername(response.getUsername());
				responseDTO.setCreatedByUserId(StringUtils.isNotBlank(response.getAgentId()) ? response.getAgentId() : response.getUserId());
}
		}

		return responseDTO;
	}

}
