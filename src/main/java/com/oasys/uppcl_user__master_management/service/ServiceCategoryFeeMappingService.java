package com.oasys.uppcl_user__master_management.service;

import java.util.List;
import java.util.UUID;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.ServiceCategoryFeeMappingRequestDTO;

public interface ServiceCategoryFeeMappingService {
	BaseDTO save(ServiceCategoryFeeMappingRequestDTO requestDTO);
	BaseDTO update(ServiceCategoryFeeMappingRequestDTO requestDTO);
	BaseDTO getByServiceId(String serviceName);
	BaseDTO getBySearchFilter(PaginationRequestDTO requestDTO);
	Double getAmountByServiceCategoryId(UUID serviceCategoryId);
	BaseDTO getTotalAmountByServiceCategoryIds(List<UUID> serviceCategoryIds);
	BaseDTO getAllRecords(Boolean status);
}
