package com.oasys.uppcl_user__master_management.mapper;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oasys.clients.userservice.utils.CommonUtil;
import com.oasys.uppcl_user__master_management.dto.PackageHistoryResponseDTO;
import com.oasys.uppcl_user__master_management.dto.ResponseContent;
import com.oasys.uppcl_user__master_management.entity.PackageHistory;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class PackageHistoryMapper {

	@Autowired
	private CommonUtil commonUtil;

	public PackageHistoryResponseDTO convertEntityToResponseDTO(PackageHistory entity) {
		PackageHistoryResponseDTO responseDTO = commonUtil.modalMap(entity, PackageHistoryResponseDTO.class);

		if (Objects.nonNull(entity.getCreatedBy())) {
			ResponseContent response = commonUtil.getUserDetailsById(entity.getCreatedBy());
			if (Objects.nonNull(response)) {
				responseDTO.setModifiedByUsername(response.getUsername());
				responseDTO.setModifiedByUserId(
						StringUtils.isNotBlank(response.getAgentId()) ? response.getAgentId() : response.getUserId());
			}
		}
		return responseDTO;
	}
}
