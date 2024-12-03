package com.oasys.uppcl_user__master_management.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oasys.uppcl_user__master_management.entity.StatusTypeMasterEntity;



public interface StatusTypeMasterRepository extends JpaRepository <StatusTypeMasterEntity, UUID>{
	@Query("select a from StatusTypeMasterEntity a where a.statusTypeName = :statusTypeName")
	 Optional<StatusTypeMasterEntity> check(@Param("statusTypeName") String statusTypeName);
	
	@Query("SELECT a FROM StatusTypeMasterEntity a WHERE UPPER(a.statusTypeName) LIKE %:value%")
	Page<StatusTypeMasterEntity> search(Pageable pageable,@Param("value") String value);
	
   @Query("select a from StatusTypeMasterEntity a where a.statusTypeName =:statusTypeName")
   List<StatusTypeMasterEntity> findByName(@Param("statusTypeName") String statusTypeName);

	@Query("SELECT a FROM StatusTypeMasterEntity a WHERE a.status =1")
	List<StatusTypeMasterEntity> getAllactive();

}
