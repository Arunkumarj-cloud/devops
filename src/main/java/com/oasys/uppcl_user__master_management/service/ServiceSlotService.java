package com.oasys.uppcl_user__master_management.service;

import java.util.UUID;

import javax.validation.Valid;

import com.oasys.config.BaseDTO;
import com.oasys.uppcl_user__master_management.dto.ServiceSlotDTO;

public interface ServiceSlotService {

	BaseDTO update(UUID id, @Valid ServiceSlotDTO dto);

	BaseDTO delete(UUID id);

	BaseDTO getAllActive();

	BaseDTO create(@Valid ServiceSlotDTO dto);

	BaseDTO getByServiceCategoryId(UUID id);

	BaseDTO getById(UUID id);
	
	BaseDTO get(UUID id,Double amount);
	
	public BaseDTO getSlotids(Double amount);

}
