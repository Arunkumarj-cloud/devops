package com.oasys.uppcl_user__master_management.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oasys.uppcl_user__master_management.entity.DistrictMasterEntity;
import com.oasys.uppcl_user__master_management.entity.Muncipality;



public interface MuncipalityRepository extends JpaRepository<Muncipality, UUID>{
	
	@Query("select a from Muncipality a where a.status = true and a.districtId =:districtId ORDER by a.municipalityName")
	 List <Muncipality> findByDistrictId(@Param("districtId") DistrictMasterEntity id);

	@Query("select m from Muncipality m where UPPER(m.municipalityName) = :name and m.districtId.id = :id")
	Optional<Muncipality> findByMunicipalityName(@Param("name") String name, @Param("id") UUID id);
	
	@Query("select a from Muncipality a where a.municipalityName =:name")
	List<Muncipality> findByName(@Param("name") String name);


}
