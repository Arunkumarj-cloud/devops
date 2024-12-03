package com.oasys.uppcl_user__master_management.service;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;

public interface ServiceCategoryFeeMappingHistoryService {

	BaseDTO getBySearchFilter(PaginationRequestDTO requestDTO);
}
