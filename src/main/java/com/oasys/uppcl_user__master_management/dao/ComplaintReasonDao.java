package com.oasys.uppcl_user__master_management.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.ComplaintReasonDTO;
import com.oasys.uppcl_user__master_management.entity.ComplaintReasonEntity;

public interface ComplaintReasonDao {

	ComplaintReasonEntity save(ComplaintReasonEntity complaintReasonEntity);

	List<ComplaintReasonEntity> getAll();

	List<ComplaintReasonEntity> getAllActive();

	BaseDTO update(UUID id, ComplaintReasonDTO complaintReasonDTO);

	ComplaintReasonEntity getById(UUID id);

	ComplaintReasonEntity delete(UUID id);

	Page<ComplaintReasonEntity> getLazyList(PaginationRequestDTO requestData);

}
