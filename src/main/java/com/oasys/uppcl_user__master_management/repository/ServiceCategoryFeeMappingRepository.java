package com.oasys.uppcl_user__master_management.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oasys.uppcl_user__master_management.entity.ServiceCategoryFeeMappingEntity;


public interface ServiceCategoryFeeMappingRepository extends JpaRepository<ServiceCategoryFeeMappingEntity, UUID>{

	
	@Query("select s from ServiceCategoryFeeMappingEntity s where s.serviceCategoryEntity.id =:serviceId")
	 Optional<ServiceCategoryFeeMappingEntity> findByServiceCategoryId(@Param("serviceId") UUID serviceId);
	
	@Query("select sum(s.amount) from ServiceCategoryFeeMappingEntity s where s.serviceCategoryEntity.id in :serviceCategoryIds")
	 Double getTotalAmountByServiceCategoryIds(@Param("serviceCategoryIds") List<UUID> serviceCategoryIds);
	
	@Query("select s.amount from ServiceCategoryFeeMappingEntity s where s.serviceCategoryEntity.id =:serviceId")
	 Double findAmountByServiceCategoryId(@Param("serviceId") UUID serviceId);
	
	@Query("select s from ServiceCategoryFeeMappingEntity s where s.serviceCategoryEntity.status = true order by s.modifiedDate desc")
	 List<ServiceCategoryFeeMappingEntity> findAllActive();
	 

}
