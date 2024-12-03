package com.oasys.uppcl_user__master_management.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oasys.uppcl_user__master_management.entity.PlanServiceMappingEntity;


public interface PlanServiceMappingRepository extends JpaRepository<PlanServiceMappingEntity, UUID> {

	@Query("SELECT p FROM PlanServiceMappingEntity p where p.planMasterEntity.id=:planId and p.planMasterEntity.isActive=true and p.serviceMasterEntity.id=:serviceId and p.serviceMasterEntity.status=true")
	Optional<PlanServiceMappingEntity> findByPlanIdAndServiceId(@Param("planId") UUID planId,
			@Param("serviceId") UUID serviceId);

	@Query("SELECT p FROM PlanServiceMappingEntity p where p.planMasterEntity.id=:planId and p.planMasterEntity.isActive=true and p.serviceMasterEntity.status=true")
	List<PlanServiceMappingEntity> findByPlanId(@Param("planId") UUID planId);

	@Modifying
	@Query("DELETE FROM PlanServiceMappingEntity p where p.planMasterEntity.id=:planId and p.serviceMasterEntity.id not in :serviceIds")
	void deleteByPlanIdAndNotInServiceIds(@Param("planId") UUID planId, @Param("serviceIds") List<UUID> serviceIds);

	@Modifying
	@Query("DELETE FROM PlanServiceMappingEntity p where p.planMasterEntity.id=:planId and p.serviceMasterEntity.id = :serviceId")
	void deleteByPlanIdAndServiceId(@Param("planId") UUID planId, @Param("serviceId") UUID serviceId);
}



