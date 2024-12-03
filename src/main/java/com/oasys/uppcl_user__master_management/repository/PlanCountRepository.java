package com.oasys.uppcl_user__master_management.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.oasys.uppcl_user__master_management.entity.PlanMasterEntity;



public interface PlanCountRepository extends JpaRepository<PlanMasterEntity, UUID>{
	
	@Query("SELECT COUNT(*) as count FROM PlanMasterEntity a where a.isActive =1")
	int getPlanCountByActiveSttus();
	
}
