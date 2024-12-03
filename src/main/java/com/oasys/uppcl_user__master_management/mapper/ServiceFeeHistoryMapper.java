package com.oasys.uppcl_user__master_management.mapper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oasys.clients.userservice.utils.CommonUtil;
import com.oasys.config.Constants;
import com.oasys.uppcl_user__master_management.dto.ResponseContent;
import com.oasys.uppcl_user__master_management.dto.ServiceCategoryFeeMappingHistoryResponseDTO;
import com.oasys.uppcl_user__master_management.entity.ServiceCategoryFeeMappingHistoryEntity;

@Component
public class ServiceFeeHistoryMapper {

	@Autowired
	private CommonUtil commonUtil;
	
	
	public ServiceCategoryFeeMappingHistoryResponseDTO convertEntityToResponseDTO(ServiceCategoryFeeMappingHistoryEntity entity) {
		ServiceCategoryFeeMappingHistoryResponseDTO responseDTO = commonUtil.modalMap(entity, ServiceCategoryFeeMappingHistoryResponseDTO.class);
		if (Objects.nonNull(entity.getCreatedDate())) {
			DateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
			responseDTO.setActionDateTime(dateFormat.format(entity.getCreatedDate()));
		}
		if (Objects.nonNull(entity.getCreatedBy())) {
			ResponseContent response = commonUtil.getUserDetailsById(entity.getCreatedBy());
			if (Objects.nonNull(response)) {
				responseDTO.setModifiedByUsername(response.getUsername());
				responseDTO.setModifiedByUserId(
						StringUtils.isNotBlank(response.getAgentId()) ? response.getAgentId() : response.getUserId());
			}
		}
		if(Objects.nonNull(entity.getServiceCategoryEntity())) {
			responseDTO.setServiceCategoryId(entity.getServiceCategoryEntity().getId());
			responseDTO.setServiceCategoryName(entity.getServiceCategoryEntity().getName());
		}
		return responseDTO;
	}
}
