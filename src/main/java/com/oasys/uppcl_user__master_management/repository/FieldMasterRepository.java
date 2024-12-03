package com.oasys.uppcl_user__master_management.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oasys.uppcl_user__master_management.entity.FieldMasterEntity;


public interface FieldMasterRepository extends JpaRepository<FieldMasterEntity, UUID> {

	@Query("select a from FieldMasterEntity a where a.fieldName= :fieldName")
	Optional<FieldMasterEntity> check(@Param("fieldName") String fieldName);
	
	@Query("SELECT a FROM FieldMasterEntity a WHERE UPPER(a.fieldName) LIKE %:value% ")
	Page<FieldMasterEntity> search(Pageable pageable, @Param("value") String value);

	@Query("SELECT a from FieldMasterEntity a where a.id= :id")
	List<FieldMasterEntity> findByFieldId(@Param("id")UUID id);

	@Query("SELECT a FROM FieldMasterEntity a WHERE a.status =1")
	List<FieldMasterEntity> getAllactive();

	FieldMasterEntity findByFieldNameIgnoreCase(String fieldName);
}
