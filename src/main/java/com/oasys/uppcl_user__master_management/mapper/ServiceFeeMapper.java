package com.oasys.uppcl_user__master_management.mapper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oasys.clients.userservice.utils.CommonUtil;
import com.oasys.config.Constants;
import com.oasys.uppcl_user__master_management.dto.ServiceCategoryFeeMappingResponseDTO;
import com.oasys.uppcl_user__master_management.entity.ServiceCategoryFeeMappingEntity;

@Component
public class ServiceFeeMapper {
	@Autowired
	private CommonUtil commonUtil;
	
	public ServiceCategoryFeeMappingResponseDTO convertEntityToResponseDTO(ServiceCategoryFeeMappingEntity entity) {
		ServiceCategoryFeeMappingResponseDTO responseDTO = commonUtil.modalMap(entity, ServiceCategoryFeeMappingResponseDTO.class);
		if (Objects.nonNull(entity.getCreatedDate())) {
			DateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
			responseDTO.setCreatedDate(dateFormat.format(entity.getCreatedDate()));
		}
		if (Objects.nonNull(entity.getModifiedDate())) {
			DateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
			responseDTO.setModifiedDate(dateFormat.format(entity.getModifiedDate()));
		}
		if(Objects.nonNull(entity.getServiceCategoryEntity())) {
			responseDTO.setServiceCategoryId(entity.getServiceCategoryEntity().getId());
			responseDTO.setServiceCategoryName(entity.getServiceCategoryEntity().getName());
			responseDTO.setIsActive(entity.getServiceCategoryEntity().getStatus());
		}
		return responseDTO;
	}
}
