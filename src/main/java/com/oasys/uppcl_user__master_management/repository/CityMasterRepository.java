package com.oasys.uppcl_user__master_management.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oasys.uppcl_user__master_management.entity.CityMasterEntity;

public interface CityMasterRepository extends  JpaRepository<CityMasterEntity, UUID> {

	@Query("select a from CityMasterEntity a where a.cityName =:name and a.stateId.id =:id and a.districtId.id=:id1")
	Optional<CityMasterEntity> check(@Param("name") String cityName,@Param("id") UUID id , @Param("id1") UUID id1);
	
//	@Query("select s from CityMasterEntity s where s.districtId.id=:id")
//	Optional<CityMasterEntity> check(@Param("id") UUID id);
	
	
	@Query("select a from CityMasterEntity a where a.cityName =:cityName")
	List<CityMasterEntity> checkCityName(@Param("cityName") String cityName);

}
