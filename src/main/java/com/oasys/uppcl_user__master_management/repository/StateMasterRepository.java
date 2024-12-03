package com.oasys.uppcl_user__master_management.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oasys.uppcl_user__master_management.entity.StateMasterEntity;


public interface StateMasterRepository extends JpaRepository<StateMasterEntity, UUID> {
	
	@Query("SELECT s FROM StateMasterEntity s WHERE s.status = true ORDER BY s.stateName ASC")
	List<StateMasterEntity> findAllActiveStates();
	
	@Query("SELECT a FROM StateMasterEntity a WHERE UPPER(a.stateName) LIKE %:value% or UPPER(a.stateCode) LIKE %:value% or UPPER(a.taxIdentificationNo) LIKE %:value% ")
	Page<StateMasterEntity> search(Pageable pageable,@Param("value") String value);

	@Query("SELECT s FROM StateMasterEntity s WHERE s.stateCode=:stateCode ")
	StateMasterEntity findByStateCode(@Param("stateCode")  String stateCode);
	
	@Query("SELECT s FROM StateMasterEntity s WHERE s.id != :id ")
	List<StateMasterEntity> findByIdNotIn(UUID id);
	
	@Query("SELECT s FROM StateMasterEntity s WHERE  UPPER(s.stateName)=:stateName and  s.status = true")
	StateMasterEntity getstateName(@Param("stateName")  String stateName);

	
}
