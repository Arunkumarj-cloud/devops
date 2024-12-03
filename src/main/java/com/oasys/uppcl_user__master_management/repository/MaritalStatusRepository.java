package com.oasys.uppcl_user__master_management.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oasys.uppcl_user__master_management.entity.MaritalStatusMasterEntity;


public interface MaritalStatusRepository extends JpaRepository<MaritalStatusMasterEntity, UUID> {

	@Query("SELECT a FROM MaritalStatusMasterEntity a WHERE a.status =1 ORDER BY a.name ASC")
	List<MaritalStatusMasterEntity> getAllactive();

	@Query("SELECT a FROM MaritalStatusMasterEntity a WHERE UPPER(a.name) LIKE %:value% ")
	Page<MaritalStatusMasterEntity> search(Pageable pageable, @Param("value") String value);

	@Query("SELECT a FROM MaritalStatusMasterEntity a WHERE UPPER(a.name) LIKE %:name% ")
	MaritalStatusMasterEntity findByName(@Param("name") String matrialStatus);

	@Query("SELECT a FROM MaritalStatusMasterEntity a WHERE a.id != :id ")
	List<MaritalStatusMasterEntity> findByIdNotIn(UUID id);
	
	MaritalStatusMasterEntity findByNameIgnoreCase(String name); 

}
