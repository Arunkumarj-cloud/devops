package com.oasys.uppcl_user__master_management.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oasys.uppcl_user__master_management.entity.FieldTypeMasterEntity;


public interface FieldTypeMasterRepository extends JpaRepository<FieldTypeMasterEntity, UUID> {

	@Query("select a from FieldTypeMasterEntity a where UPPER(a.fieldTypeName) LIKE %:fieldTypeName%")
	Optional<FieldTypeMasterEntity> check(@Param("fieldTypeName") String fieldTypeName);

	@Query("SELECT a FROM FieldTypeMasterEntity a WHERE UPPER(a.fieldTypeName) LIKE %:value%")
	Page<FieldTypeMasterEntity> search(Pageable pageRequest,@Param("value") String search);

	@Query("select a from FieldTypeMasterEntity a where UPPER(a.fieldTypeName) LIKE %:name%")
	List<FieldTypeMasterEntity> getFieldTypeName(@Param("name") String name);

	@Query("SELECT a FROM FieldTypeMasterEntity a WHERE a.status =1")
	List<FieldTypeMasterEntity> getAllactive();

	FieldTypeMasterEntity findByFieldTypeNameIgnoreCase(String fieldTypeName); 
}


