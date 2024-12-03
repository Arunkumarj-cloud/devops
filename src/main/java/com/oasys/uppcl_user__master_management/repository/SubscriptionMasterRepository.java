package com.oasys.uppcl_user__master_management.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oasys.uppcl_user__master_management.entity.SubscriptionMasterEntity;



public interface SubscriptionMasterRepository extends JpaRepository<SubscriptionMasterEntity, UUID> {
	
	@Query("select a from SubscriptionMasterEntity a where a.isActive = true ORDER BY a.subscriptionName ASC")
	List<SubscriptionMasterEntity> findByIsActiveTrue();
	
	@Query("SELECT s FROM SubscriptionMasterEntity s WHERE UPPER(s.subscriptionName) LIKE %:value% or UPPER(s.isCustom) LIKE %:value% "
			+ "or UPPER(s.isDefault) LIKE %:value% or UPPER(s.numOfDays) LIKE %:value% or UPPER(s.description) LIKE %:value%")
	Page<SubscriptionMasterEntity> search(Pageable pageable, @Param("value") String value);
	
    SubscriptionMasterEntity findByIsDefaultTrue();
	
    @Query("select a from SubscriptionMasterEntity a where a.id != :id")
	List<SubscriptionMasterEntity> findByIdNotEqualToId(UUID id);
		
	SubscriptionMasterEntity findBySubscriptionNameIgnoreCase(String subscriptionName);

	List<SubscriptionMasterEntity> findByIdIn(List<UUID> list);

	@Query("select a from SubscriptionMasterEntity a where a.isDefault = true")
	List<SubscriptionMasterEntity> getDefault();

	@Query("select a from SubscriptionMasterEntity a where a.subscriptionName = :name")
	List<SubscriptionMasterEntity> check(@Param("name") String name);
	
	@Query("select a from SubscriptionMasterEntity a where a.subscriptionName = :name")
	SubscriptionMasterEntity findBySubNameIgnoreCase(@Param("name") String name);
	
	//SubscriptionMasterEntity findBySubNameIgnoreCase(String subscriptionName);
}
