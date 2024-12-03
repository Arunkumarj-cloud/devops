package com.oasys.uppcl_user__master_management.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.oasys.uppcl_user__master_management.entity.NatureOfBusinessMasterEntity;
@Repository
public interface NatureOfBusinessRepository extends JpaRepository<NatureOfBusinessMasterEntity, UUID> { 
	
		
		@Query("SELECT a FROM NatureOfBusinessMasterEntity a WHERE a.status =1 ORDER BY a.name ASC")
	List<NatureOfBusinessMasterEntity> getAllactive();
//
//
	 	@Query("SELECT a FROM NatureOfBusinessMasterEntity a WHERE UPPER(a.name) LIKE %:value% or UPPER(a.code) LIKE %:value%")
	Page<NatureOfBusinessMasterEntity> search(Pageable pageable,@Param("value") String value);
//
	@Query("SELECT a FROM NatureOfBusinessMasterEntity a WHERE a.id != :id")
	List<NatureOfBusinessMasterEntity> findByIdNotIn(UUID id);
//		
	//	@Query("SELECT a FROM NatureOfBusinessMasterEntity a WHERE UPPER(a.name) LIKE %:name%")
	//	NatureOfBusinessMasterEntity findByName(@Param("name") String name);
//		
//
		@Query("select a from NatureOfBusinessMasterEntity a where UPPER(a.name) LIKE %:name%")
	 List<NatureOfBusinessMasterEntity> check(@Param("name") String name);
//	
	//@Query("select a from Nobmentity a where UPPER(a.name) LIKE %:name%")
		NatureOfBusinessMasterEntity findByNameIgnoreCase(String name);
		
		@Query("select a from NatureOfBusinessMasterEntity a where UPPER(a.code) =:code")
		Optional<NatureOfBusinessMasterEntity> findByMccCode(@Param("code") String mccCode);
		
	}

