package com.oasys.uppcl_user__master_management.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oasys.uppcl_user__master_management.entity.TemplateViewEntity;



public interface TemplateViewRepo extends JpaRepository<TemplateViewEntity, UUID> {

	@Query("SELECT a FROM TemplateViewEntity a WHERE UPPER(a.applicationName) LIKE %:value% or"
			+ " UPPER(a.name) LIKE %:value% or UPPER(a.notificationChannel) LIKE %:value% or UPPER(a.notificationNetwork) LIKE %:value% ")
	Page<TemplateViewEntity> search(Pageable pageRequest, @Param("value") String value);

}
