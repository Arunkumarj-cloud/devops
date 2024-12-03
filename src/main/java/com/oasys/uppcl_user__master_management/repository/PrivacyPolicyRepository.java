package com.oasys.uppcl_user__master_management.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oasys.uppcl_user__master_management.entity.PrivacyPolicyEntity;



public interface PrivacyPolicyRepository extends JpaRepository<PrivacyPolicyEntity, UUID>{
	
	@Query("SELECT a FROM PrivacyPolicyEntity a WHERE a.status = true")
	List<PrivacyPolicyEntity> getAllActive();
	
	@Query("SELECT mm FROM PrivacyPolicyEntity mm where UPPER(mm.description) like %:value% " + "or UPPER(mm.createdDate) like %:value%")
	Page<PrivacyPolicyEntity> search(Pageable pageable, @Param("value") String value);

	@Query("SELECT a FROM PrivacyPolicyEntity a WHERE NOT a.id =:id")
	List<PrivacyPolicyEntity> getByExceptId(@Param("id") UUID id);
	
	@Query("Select a from PrivacyPolicyEntity a where a.id=:id")
	PrivacyPolicyEntity findOne(@Param("id") UUID id);
	
	@Query("Select a from PrivacyPolicyEntity a where a.status = true")
	PrivacyPolicyEntity getOne();

	
}
