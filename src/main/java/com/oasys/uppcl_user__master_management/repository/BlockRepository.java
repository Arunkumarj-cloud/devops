package com.oasys.uppcl_user__master_management.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oasys.uppcl_user__master_management.entity.BlockMasterEntity;
import com.oasys.uppcl_user__master_management.entity.DistrictMasterEntity;

public interface BlockRepository extends JpaRepository<BlockMasterEntity, UUID> {

	@Query("select a from BlockMasterEntity a where a.status = true ORDER by a.blockName")
	 List <BlockMasterEntity> getAllActive();
	
	@Query("select a from BlockMasterEntity a where a.status = true and a.districtId =:districtId ORDER by a.blockName")
	 List <BlockMasterEntity> findByDistrictId(@Param("districtId") DistrictMasterEntity id);
	
//	@Query("select a from BlockMasterEntity a where a.status = true and a.districtId.stateId.status =true ORDER by a.name")
//	 List <BlockMasterEntity> findAllStateActive();
	
	@Query("select a from BlockMasterEntity a where a.districtId.status = true and a.districtId.stateId.status =true")
	 Page <BlockMasterEntity> findAllStateActive(Pageable pageRequest);

	@Query("SELECT a FROM BlockMasterEntity a WHERE NOT a.id =:id")
	List<BlockMasterEntity> getByExceptId(@Param("id") UUID id);
	
	@Query("select a from BlockMasterEntity a where a.blockCode =:code")
	List<BlockMasterEntity> checkCode(@Param("code") String code);

}
