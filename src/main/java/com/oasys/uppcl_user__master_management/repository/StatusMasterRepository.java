package com.oasys.uppcl_user__master_management.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oasys.uppcl_user__master_management.entity.StatusMasterEntity;



public interface StatusMasterRepository extends JpaRepository <StatusMasterEntity, UUID> {

	@Query("select a from StatusMasterEntity a where a.statusName = :statusName")
	 Optional<StatusMasterEntity> check(@Param("statusName") String statusName);
	
	@Query("SELECT a FROM StatusMasterEntity a WHERE UPPER(a.statusName) LIKE %:value% ")
	Page<StatusMasterEntity> search(Pageable pageable,@Param("value") String value);

	@Query("SELECT a FROM StatusMasterEntity a WHERE a.status =1")
	List<StatusMasterEntity> getAllactive();

	@Query("select a from StatusMasterEntity a where a.statusName =:name")
	List<StatusMasterEntity> findByName(@Param("name") String name);

}
