package com.oasys.uppcl_user__master_management.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oasys.uppcl_user__master_management.entity.ServiceMasterEntity;

public interface ServiceRepository extends JpaRepository<ServiceMasterEntity, UUID> {

	@Query("select a from ServiceMasterEntity a where a.status = true ORDER by a.serviceName")
	List<ServiceMasterEntity> getAllActive();

	@Query("select a from ServiceMasterEntity a where a.serviceNumber = :number")
	ServiceMasterEntity getServiceNumber(@Param("number") String number);

	@Query("select a from ServiceMasterEntity a where UPPER(a.serviceName) LIKE %:name%")
	ServiceMasterEntity getServiceName(@Param("name") String name);

	@Query("select a from ServiceMasterEntity a where a.baseURL = :url")
	ServiceMasterEntity getBaseURL(@Param("url") String url);

	@Query("select a from ServiceMasterEntity a where a.id not in :id")
	List<ServiceMasterEntity> getByExceptId(@Param("id") UUID id);

	@Query("select a from ServiceMasterEntity a where a.serviceTypeId.id = :id and a.status = true ORDER BY a.createdDate ASC ")
	List<ServiceMasterEntity> findByServiceTypeId(@Param("id") UUID id);

	@Query("SELECT COUNT(*) as count FROM ServiceMasterEntity a where a.status = true")
	int getServiceCount();

	@Query("select a from ServiceMasterEntity a where a.status = true and a.serviceName=:search ORDER by a.serviceName")
	List<ServiceMasterEntity> getAllActiveWithSearch(@Param("search") String search);
	
	@Query("select a from ServiceMasterEntity a where a.serviceName =:name")
	Optional<ServiceMasterEntity> findByServiceName(@Param("name") String name);
}
