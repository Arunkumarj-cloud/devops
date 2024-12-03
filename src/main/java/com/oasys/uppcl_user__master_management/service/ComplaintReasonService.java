package com.oasys.uppcl_user__master_management.service;

import java.util.UUID;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.ComplaintReasonDTO;

public interface ComplaintReasonService {

	BaseDTO create(ComplaintReasonDTO complaintReasonDTO);

	BaseDTO getAll();

	BaseDTO getAllActive();

	BaseDTO update(UUID id, ComplaintReasonDTO complaintReasonDTO);

	BaseDTO getLazyList(PaginationRequestDTO requestData);

	BaseDTO getById(UUID id);

	BaseDTO delete(UUID id);

	BaseDTO softDelete(UUID id);

}
