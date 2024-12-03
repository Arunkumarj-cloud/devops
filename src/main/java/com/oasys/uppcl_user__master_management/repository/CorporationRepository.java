package com.oasys.uppcl_user__master_management.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oasys.uppcl_user__master_management.entity.CorporationMasterEntity;
import com.oasys.uppcl_user__master_management.entity.DistrictMasterEntity;

public interface CorporationRepository extends JpaRepository<CorporationMasterEntity, UUID> {

	@Query("select a from CorporationMasterEntity a where a.status = true ORDER by a.corporationName")
	 List <CorporationMasterEntity> getAllActive();
	
	@Query("select a from CorporationMasterEntity a where a.status = true and a.districtId =:districtId ORDER by a.corporationName")
	 List <CorporationMasterEntity> findByDistrictId(@Param("districtId") DistrictMasterEntity id);
	
	@Query("select a from CorporationMasterEntity a where a.corporationCode =:code")
	 List <CorporationMasterEntity> checkCode(@Param("code") String code);
	

}
