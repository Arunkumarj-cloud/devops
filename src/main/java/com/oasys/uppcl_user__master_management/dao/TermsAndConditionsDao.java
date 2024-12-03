package com.oasys.uppcl_user__master_management.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.entity.TermsAndConditionsEntity;


public interface TermsAndConditionsDao {
	
	TermsAndConditionsEntity save(TermsAndConditionsEntity termsList);

	TermsAndConditionsEntity getById(UUID id);
	
	TermsAndConditionsEntity delete(UUID id);

	Page<TermsAndConditionsEntity> getLazyList(PaginationRequestDTO requestData);

	List<TermsAndConditionsEntity> exceptId(UUID id);

	List<TermsAndConditionsEntity> getAll();
	
	List<TermsAndConditionsEntity> getAllActive();

	TermsAndConditionsEntity getOne();

}
