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

import com.oasys.uppcl_user__master_management.entity.EducationEntity;
import com.oasys.uppcl_user__master_management.entity.GenderMasterEntity;

@Repository
public interface GenderMasterRepository extends JpaRepository<GenderMasterEntity,UUID>{

	@Query("select  a from GenderMasterEntity  a where UPPER(a.name) LIKE %:name%")
	List<GenderMasterEntity> findByGenderName(@Param("name") String name);

	@Query("SELECT a FROM GenderMasterEntity a WHERE UPPER(a.name) LIKE %:value% ")
	Page<GenderMasterEntity> search(Pageable pageable,@Param("value") String value);
	
	@Query("select a from GenderMasterEntity a where  a.name =:name")
	List<GenderMasterEntity> findByName(@Param("name") String name);

	@Query("SELECT a FROM GenderMasterEntity a WHERE a.status =1")
	List<GenderMasterEntity> getAllActive();
	
	GenderMasterEntity findByNameIgnoreCase(String name);
	


}