package com.oasys.uppcl_user__master_management.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.entity.ReasonTypeEntity;


public interface ReasonTypeDao {
	
	ReasonTypeEntity save(ReasonTypeEntity reasonTypeEntity);

	ReasonTypeEntity getById(UUID id);

	List<ReasonTypeEntity> getAll();

	List<ReasonTypeEntity> getAllActive();

	Page<ReasonTypeEntity> getLazyList(PaginationRequestDTO requestData);

	ReasonTypeEntity delete(UUID id );
	
	ReasonTypeEntity validateReasonType(String subscriptionName);
	
	List<ReasonTypeEntity> exceptId(UUID id);
	
	Optional<ReasonTypeEntity> findByReasonTypeId(UUID id);
}



