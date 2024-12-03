package com.oasys.uppcl_user__master_management.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oasys.uppcl_user__master_management.entity.ApiMasterEntity;



public interface ApiDetailsRepository extends JpaRepository<ApiMasterEntity, UUID> {

	
	
	
	@Query("SELECT api FROM ApiMasterEntity api WHERE UPPER(api.apiName) LIKE %:search% or UPPER(api.contentType) LIKE %:search% or "
			+ "UPPER(api.description) LIKE %:search% or UPPER(api.feature) LIKE %:search% or UPPER(api.module) LIKE %:search% "
			+ "or UPPER(api.requestMethod) LIKE %:search% or UPPER(api.url) LIKE %:search% or UPPER(api.responseBodyType) LIKE %:search%")
	Page<ApiMasterEntity> search(Pageable pageable, @Param("search") String search);	
	
	  @Query("select a from ApiMasterEntity a where a.status = true ") List
	  <ApiMasterEntity> getAllActive();
	  
	  
	  
	  @Query("SELECT a FROM ApiMasterEntity a WHERE a.apiName = :name")
	  List<ApiMasterEntity> checkApiName( @ Param ("name") String apiName );
	 
}
