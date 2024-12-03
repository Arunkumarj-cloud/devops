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

@Repository

public interface EducationRepository extends JpaRepository<EducationEntity, UUID>{
	
	@Query("SELECT a FROM EducationEntity a WHERE a.status =1")
	List<EducationEntity> getAllActive();
	
	@Query("SELECT a FROM EducationEntity a WHERE UPPER(a.name) LIKE %:value% ")
	Page<EducationEntity> search(Pageable pageable,@Param("value") String value);
	
	@Query("select a from EducationEntity a where a.name =:name")
	List<EducationEntity> findByName(@Param("name") String name);

	@Query("select a from EducationEntity a where  a.name =:name ") 
	Optional<EducationEntity> findByEducationName(@Param("name") String name);
	
	EducationEntity findByNameIgnoreCase(String name);
	

}
