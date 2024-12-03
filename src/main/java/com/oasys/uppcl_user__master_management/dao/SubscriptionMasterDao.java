package com.oasys.uppcl_user__master_management.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.entity.SubscriptionMasterEntity;


public interface SubscriptionMasterDao {
	
	SubscriptionMasterEntity save(SubscriptionMasterEntity subscriptionMasterEntity);

	SubscriptionMasterEntity getById(UUID id);

	List<SubscriptionMasterEntity> getAll();

	List<SubscriptionMasterEntity> getAllActive();

	Page<SubscriptionMasterEntity> getLazyList(PaginationRequestDTO requestData);

	SubscriptionMasterEntity delete(UUID id );
	
	SubscriptionMasterEntity validateSubscriptionName(String subscriptionName);
	
	SubscriptionMasterEntity validateIsDefault();
	
	List<SubscriptionMasterEntity> exceptId(UUID id);
	
	List<SubscriptionMasterEntity> getDefault();
	
	List<SubscriptionMasterEntity> check(String name);

}
