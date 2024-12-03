package com.oasys.uppcl_user__master_management.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oasys.uppcl_user__master_management.entity.BranchEntity;


public interface BranchRepository extends JpaRepository<BranchEntity, UUID> {
	
	@Query("SELECT a FROM BranchEntity a where  UPPER(a.ifsccode) =:ifsccode")
	List<BranchEntity> getBankid(@Param("ifsccode") String ifsc);


}
