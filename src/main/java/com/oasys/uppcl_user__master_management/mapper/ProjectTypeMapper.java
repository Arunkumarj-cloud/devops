package com.oasys.uppcl_user__master_management.mapper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oasys.clients.userservice.utils.CommonUtil;
import com.oasys.config.Constants;
import com.oasys.uppcl_user__master_management.dto.ProjectTypeResponseDTO;
import com.oasys.uppcl_user__master_management.entity.ProjectTypeEntity;


@Component
public class ProjectTypeMapper {

	@Autowired
	private CommonUtil commonUtil;

	public ProjectTypeResponseDTO convertEntityToResponseDTO(ProjectTypeEntity entity) {
		ProjectTypeResponseDTO responseDTO = commonUtil.modalMap(entity, ProjectTypeResponseDTO.class);
		
		if (Objects.nonNull(entity.getCreatedDate())) {
			DateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
			responseDTO.setCreatedDate(dateFormat.format(entity.getCreatedDate()));
		}
		if (Objects.nonNull(entity.getModifiedDate())) {
			DateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
			responseDTO.setModifiedDate(dateFormat.format(entity.getModifiedDate()));
		}
		return responseDTO;
	}
}
