package com.oasys.uppcl_user__master_management.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oasys.uppcl_user__master_management.entity.TemplateEntity;


public interface TemlateRepo extends JpaRepository<TemplateEntity, UUID> {

	@Query("SELECT a FROM TemplateEntity a WHERE a.name = :name")
	Optional<TemplateEntity> findByName(@Param("name") String name);

	@Query("SELECT a FROM TemplateEntity a WHERE a.status =1")
	List<TemplateEntity> getAllactive();
	
	@Query("select a from TemplateEntity a where a.name = :name")
	 Optional<TemplateEntity> check(@Param("name") String name);
	
	@Query("select a from TemplateEntity a where a.name =:name")
	List<TemplateEntity> check1(@Param("name") String name);
	
	@Query("SELECT a FROM TemplateEntity a WHERE UPPER(a.name) LIKE %:value% or UPPER(a.type) LIKE %:value% or UPPER(a.notificationNetwork) LIKE %:value% ")
	Page<TemplateEntity> search(Pageable pageRequest, @Param("value") String value);
	
	TemplateEntity findByNameIgnoreCase(String name);
	
	@Query("select a from TemplateEntity a where a.id=:id")
	TemplateEntity findById1(@Param("id") UUID id);


}
