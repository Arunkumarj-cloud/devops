package com.oasys.uppcl_user__master_management.dao;

import java.util.UUID;

import com.oasys.config.BaseDTO;
import com.oasys.uppcl_user__master_management.dto.ServiceSlotDTO;

public interface ServiceSlotDao {

	BaseDTO getAllActive();

	BaseDTO create(ServiceSlotDTO dto);

	BaseDTO delete(UUID id);

	BaseDTO update(UUID id, ServiceSlotDTO dto);

	BaseDTO getByServiceCategoryId(UUID id);

	BaseDTO getById(UUID id);

}
