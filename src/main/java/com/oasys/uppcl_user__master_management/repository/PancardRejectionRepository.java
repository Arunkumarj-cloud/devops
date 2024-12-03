package com.oasys.uppcl_user__master_management.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.oasys.uppcl_user__master_management.entity.PancardRejectionReasonEntity;




	public interface PancardRejectionRepository extends JpaRepository<PancardRejectionReasonEntity, UUID> {
		
		@Query("select a from PancardRejectionReasonEntity a where a.status = 1 ")
		List<PancardRejectionReasonEntity> getRejectionPanCardList();
}
