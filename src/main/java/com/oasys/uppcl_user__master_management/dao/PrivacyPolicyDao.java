package com.oasys.uppcl_user__master_management.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.entity.PrivacyPolicyEntity;


public interface PrivacyPolicyDao {

	PrivacyPolicyEntity save(PrivacyPolicyEntity privacyPolicyEntity);

	PrivacyPolicyEntity delete(UUID id);

	PrivacyPolicyEntity getById(UUID id);

	List<PrivacyPolicyEntity> getAll();

	Page<PrivacyPolicyEntity> getLazyList(PaginationRequestDTO requestData);

	List<PrivacyPolicyEntity> getAllActive();
	
	List<PrivacyPolicyEntity> exceptId(UUID id);

	PrivacyPolicyEntity getOne();

}
