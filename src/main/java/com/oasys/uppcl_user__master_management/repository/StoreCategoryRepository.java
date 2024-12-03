package com.oasys.uppcl_user__master_management.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oasys.uppcl_user__master_management.entity.StoreCategoryEntity;



public interface StoreCategoryRepository extends JpaRepository<StoreCategoryEntity, UUID> {
	
	@Query("SELECT a FROM StoreCategoryEntity a WHERE a.status =1")
	List<StoreCategoryEntity> getAllactive();
	
	@Query("SELECT a FROM StoreCategoryEntity a WHERE a.natureOfBusinessId.status = 1 and (UPPER(a.natureOfBusinessId.name) "
			+ "LIKE %:value% or UPPER(a.storeCategoryName) LIKE %:value% or UPPER(a.description) LIKE %:value%)")
	Page<StoreCategoryEntity> search(Pageable pageable,@Param("value") String value);

	StoreCategoryEntity findByStoreCategoryName(String name);
	
	@Query("SELECT a FROM StoreCategoryEntity a WHERE a.id != :id")
	List<StoreCategoryEntity> findByIdNotIn(UUID id);

}
