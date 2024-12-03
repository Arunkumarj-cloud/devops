package com.oasys.uppcl_user__master_management.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oasys.uppcl_user__master_management.entity.CommissionHierarchyEntity;

public interface CommissionHierarchyRepository extends JpaRepository<CommissionHierarchyEntity, UUID> {

	@Query("select a from CommissionHierarchyEntity a where a.status =:status ORDER by a.modifiedDate ASC")
	List<CommissionHierarchyEntity> getAllActive(@Param("status") Boolean status);


	@Query("select a from CommissionHierarchyEntity a ORDER by a.modifiedDate ASC")
	List<CommissionHierarchyEntity> getAllActive();
}
