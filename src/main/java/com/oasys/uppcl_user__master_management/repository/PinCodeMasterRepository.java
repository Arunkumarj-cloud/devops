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
import com.oasys.uppcl_user__master_management.entity.PinCodeMasterEntity;



public interface PinCodeMasterRepository extends JpaRepository<PinCodeMasterEntity, UUID> {

	@Query("select a from PinCodeMasterEntity a where a.status = true ORDER by a.pinCode")
	List<PinCodeMasterEntity> getAllActive();

	@Query("select a from PinCodeMasterEntity a where a.status = true and a.districtId =:districtId ORDER by a.pinCode ASC")
	List<PinCodeMasterEntity> findByDistrictId(@Param("districtId") DistrictMasterEntity districtMasterEntity);

	@Query("select a from PinCodeMasterEntity a where a.districtId.status = true")
	Page<PinCodeMasterEntity> findAllDistrcitActive(Pageable pageRequest);

	@Query("select a from PinCodeMasterEntity a where a.districtId.status = true and UPPER(a.pinCode) LIKE %:value% or "
			+ " UPPER(a.divisionName) LIKE %:value% or UPPER(a.districtId.districtName) LIKE %:value%")
	Page<PinCodeMasterEntity> search(Pageable pageRequest, @Param("value") String value);

	@Query("SELECT a FROM PinCodeMasterEntity a WHERE NOT a.id =:id")
	List<PinCodeMasterEntity> getByExceptId(@Param("id") UUID id);
	
	@Query("select a from PinCodeMasterEntity a where a.id =:id ")
	 List <PinCodeMasterEntity> findByPinCodeId(@Param("id") UUID id);
	
	@Query("SELECT s FROM PinCodeMasterEntity s WHERE s.divisionName=:divisionName")
	PinCodeMasterEntity findByDivisonName(@Param("divisionName")  String divisionName);
	
	List<PinCodeMasterEntity> findByIdIn(Set<UUID> ids);
	
	Optional<PinCodeMasterEntity> findByPinCode(String pinCode);
	

	@Query("SELECT a FROM PinCodeMasterEntity a WHERE a.divisionName LIKE %:value% or UPPER(a.pinCode)"
			+ " LIKE %:value%   or a.districtId.districtName LIKE %:value%  or a.districtId.stateId.stateName LIKE %:value% ")
	Page<PinCodeMasterEntity> search1(Pageable pageable,@Param("value") String value);
	
	@Query("SELECT s FROM PinCodeMasterEntity s WHERE s.pinCode=:pinCode")
	PinCodeMasterEntity findByDistrictCode(@Param("pinCode")  Integer pinCode);
	
	
	
	
	@Query("SELECT s FROM PinCodeMasterEntity s WHERE s.pinCode=:pinCode")
	List<PinCodeMasterEntity> findByPinCode3( @Param ("pinCode") String pinCode);
	
	@Query("SELECT s FROM PinCodeMasterEntity s WHERE s.pinCode=:pinCode")
	PinCodeMasterEntity findByPinCode1( @Param ("pinCode") String pinCode);

	//PinCodeMasterEntity findByPinCode(Integer pinCode);
	

	@Query("SELECT s FROM PinCodeMasterEntity s WHERE s.pinCode=:pinCode")
	PinCodeMasterEntity findByPinCode2( @Param ("pinCode") String pinCode);

	

}
