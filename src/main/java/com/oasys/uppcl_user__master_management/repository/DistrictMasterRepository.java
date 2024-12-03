package com.oasys.uppcl_user__master_management.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oasys.uppcl_user__master_management.entity.DistrictMasterEntity;
import com.oasys.uppcl_user__master_management.entity.StateMasterEntity;


public interface DistrictMasterRepository extends JpaRepository<DistrictMasterEntity, UUID> {

	
	@Query("select a from DistrictMasterEntity a where a.status = true ORDER by a.districtName")
	List<DistrictMasterEntity> getAllActive();

	@Query("select a from DistrictMasterEntity a where a.status = true and a.stateId =:stateid ORDER by a.districtName ASC")
	List<DistrictMasterEntity> findByStateId(@Param("stateid") StateMasterEntity stateid);

	@Query("select a from DistrictMasterEntity a where a.stateId.status = true")
	Page<DistrictMasterEntity> findAllStateActive(Pageable pageRequest);

	@Query("select a from DistrictMasterEntity a where a.districtCode =:code")
	List<DistrictMasterEntity> checkCode(@Param("code") String code);

	@Query("select a from DistrictMasterEntity a where a.stateId.status = true and UPPER(a.districtName) LIKE %:value% or "
			+ " UPPER(a.districtCode) LIKE %:value% or UPPER(a.stateId.stateName) LIKE %:value%")
	Page<DistrictMasterEntity> search(Pageable pageRequest, @Param("value") String value);

	@Query("select a from DistrictMasterEntity a where UPPER(a.districtName) =:name and a.stateId.id =:id")
	Optional<DistrictMasterEntity> check(@Param("name") String districtName , @Param("id") UUID id);

	@Query("SELECT a FROM DistrictMasterEntity a WHERE NOT a.id =:id")
	List<DistrictMasterEntity> getByExceptId(@Param("id") UUID id);
	
	@Query("select a from DistrictMasterEntity a where a.id =:id ")
	 List<DistrictMasterEntity> findByDistrictId(@Param("id") UUID id);
	
	@Query("SELECT s FROM DistrictMasterEntity s WHERE s.districtName=:districtName")
	DistrictMasterEntity findByDistrictCode(@Param("districtName")  String districtName);
	
	List<DistrictMasterEntity> findByIdIn(Set<UUID> ids);
	
	@Query("SELECT a FROM DistrictMasterEntity a WHERE a.districtName = :name")
	List<DistrictMasterEntity> findByDistrictName(@Param("name") String name);
	
	@Query("SELECT s.id FROM DistrictMasterEntity s WHERE s.districtName=:districtName and s.stateId.id=:stateId")
	DistrictMasterEntity findByDistrictAndStateId(@Param("districtName") String districName,@Param("stateId") UUID stateId);
	
	@Query("SELECT a FROM DistrictMasterEntity a WHERE a.districtName = :name")
	List<DistrictMasterEntity> findAllByDistrictName(@Param("name") String name);
}
