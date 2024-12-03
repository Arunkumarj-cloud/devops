package com.oasys.uppcl_user__master_management.service;

import java.util.UUID;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.VillageMasterDTO;


public interface VillageMasterService {

	BaseDTO create(VillageMasterDTO villageMasterDTO);

	BaseDTO getAll();

	BaseDTO getAllActive();

	BaseDTO update(UUID id,VillageMasterDTO villageMasterDTO);

	BaseDTO getById(UUID id);

	BaseDTO delete(UUID id);

	BaseDTO getLazyList(PaginationRequestDTO requestData);

	BaseDTO softDelete(UUID id);

	BaseDTO getByTalukId(UUID id);

}
