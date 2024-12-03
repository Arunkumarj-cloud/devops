package com.oasys.uppcl_user__master_management.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.oasys.uppcl_user__master_management.entity.WardMasterEntity;



public interface WardMasterRepository extends JpaRepository<WardMasterEntity, UUID> {
	
	@Query("SELECT dm FROM WardMasterEntity dm WHERE dm.status= true")
	List<WardMasterEntity> getAllactive();
	
	WardMasterEntity findByWardName(String name);
	
	@Query("SELECT dm FROM WardMasterEntity dm WHERE dm.id != id")
	List<WardMasterEntity> findByIdNotIn(UUID id);
	
}
