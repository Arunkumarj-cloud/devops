package com.oasys.uppcl_user__master_management.repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.oasys.uppcl_user__master_management.entity.ErrorCodeEntity;

@Repository
public interface ErrorCodeMasterRepository extends JpaRepository<ErrorCodeEntity, UUID>{

	
	@Query("select u from ErrorCodeEntity u where u.responseCode = :id")
	ErrorCodeEntity getErrorCode(@Param("id")String id);

	@Query("select a from ErrorCodeEntity a where a.bhimAadhaarPay =:name")
	List<ErrorCodeEntity> findByName(@Param("name") String string);
	
	@Query("select u from ErrorCodeEntity u where u.responseCode = :id")
	List<ErrorCodeEntity> findByResponse(@Param("id")String id);
	
	@Query("select u from ErrorCodeEntity u where u.responseCode = :id")
	Set<ErrorCodeEntity> findByResponseWithoutDuplicate(@Param("id")String id);
	
	
	
}
