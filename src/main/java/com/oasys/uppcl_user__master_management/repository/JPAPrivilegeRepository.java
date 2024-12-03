package com.oasys.uppcl_user__master_management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.oasys.uppcl_user__master_management.entity.CorporationMasterEntity;
import com.oasys.uppcl_user__master_management.entity.DistrictMasterEntity;
import com.oasys.uppcl_user__master_management.entity.PrivilegeEntity;

@Repository
public interface JPAPrivilegeRepository extends JpaRepository<PrivilegeEntity, String> {

	public List<PrivilegeEntity> findAllByOrderByCreatedDateAsc();
	
	@Query("select a from PrivilegeEntity a where a.privilegeName = :privilage")
		 List <PrivilegeEntity> findByprivilegeName(@Param("privilage") String privilage);
	
	
}
