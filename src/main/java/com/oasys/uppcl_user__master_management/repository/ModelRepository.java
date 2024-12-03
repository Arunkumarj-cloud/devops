package com.oasys.uppcl_user__master_management.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.oasys.uppcl_user__master_management.entity.DistributorModelEntity;

public interface ModelRepository extends JpaRepository<DistributorModelEntity, UUID> {

	@Query("select a from DistributorModelEntity a ")
	DistributorModelEntity getData();	
}
