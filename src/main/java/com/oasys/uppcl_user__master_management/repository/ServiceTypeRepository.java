package com.oasys.uppcl_user__master_management.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oasys.uppcl_user__master_management.entity.ServiceTypeMasterEntity;


public interface ServiceTypeRepository extends JpaRepository<ServiceTypeMasterEntity, UUID> {
	
	@Query("select a from ServiceTypeMasterEntity a where a.status = true ORDER by a.serviceType")
	 List <ServiceTypeMasterEntity> getAllActive();
	
	@Query("select a from ServiceTypeMasterEntity a where UPPER(a.serviceType) LIKE %:value%")
	 Page <ServiceTypeMasterEntity> search(Pageable pageable,@Param("value") String value);

	ServiceTypeMasterEntity findByServiceType(String serviceType);
	
	@Query("select a from ServiceTypeMasterEntity a where a.id != :id")
	List<ServiceTypeMasterEntity> findByIdNotIn(UUID id);
	
	ServiceTypeMasterEntity findByServiceTypeIgnoreCase(String serviceType);
}
