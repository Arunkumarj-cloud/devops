package com.oasys.uppcl_user__master_management.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oasys.uppcl_user__master_management.entity.ServiceCategoryEntity;


public interface ServiceCategoryRepository extends JpaRepository<ServiceCategoryEntity, UUID> {
	
	@Query("select a from ServiceCategoryEntity a where UPPER(a.name) LIKE %:name%")
	 List<ServiceCategoryEntity> check(@Param("name") String name);
	
	@Query("select a from ServiceCategoryEntity a where UPPER(a.name) LIKE %:name%")
	ServiceCategoryEntity getByName(@Param("name") String name);
	
	@Query("select a from ServiceCategoryEntity a where a.status = true ORDER by a.name ASC")
	 List<ServiceCategoryEntity> getAllActive();

	Optional<ServiceCategoryEntity> findByName(String name);
	
	ServiceCategoryEntity findByNameIgnoreCase(String name);
	
	@Query("select a from ServiceCategoryEntity a ORDER by a.modifiedDate DESC")
	List<ServiceCategoryEntity> getAllOrderByModifiedDate();
	
//	a where a.status = true{

}
