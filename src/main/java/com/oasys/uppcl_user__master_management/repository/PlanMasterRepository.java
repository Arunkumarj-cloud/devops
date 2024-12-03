package com.oasys.uppcl_user__master_management.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oasys.uppcl_user__master_management.entity.PlanMasterEntity;



public interface PlanMasterRepository extends JpaRepository<PlanMasterEntity, UUID> {

	@Query("SELECT a FROM PlanMasterEntity a WHERE a.isActive =1")
	List<PlanMasterEntity> getAllactive();

	@Query("SELECT a FROM PlanMasterEntity a ORDER BY a.createdDate Desc")
	List<PlanMasterEntity> findAll();

	@Query("SELECT mm FROM PlanMasterEntity mm where UPPER(mm.planName) LIKE %:value% ")
	Page<PlanMasterEntity> search(Pageable pageable, @Param("value") String value);

	// List<PlanMasterEntity> findByIdIn(List<UUID> list);

	@Query("SELECT a FROM PlanMasterEntity a where a.id !=:id")
	List<PlanMasterEntity> getByIdNotEquatToId(UUID id);

	PlanMasterEntity findByIsDefaultTrue();
	
	PlanMasterEntity findByPlanNameIgnoreCase(String planName);

	List<PlanMasterEntity> findByIdIn(List<UUID> list);

	@Query("select a from PlanMasterEntity a where a.isDefault = true")
	List<PlanMasterEntity> check();
	
	@Query("SELECT a FROM PlanMasterEntity a where a.isActive= true ORDER BY a.planName ASC")
	List<PlanMasterEntity> findAllActive();

	@Query("select a from PlanMasterEntity a where a.id = :id")
	PlanMasterEntity findByPlanId(@Param("id") UUID uuid);
	
	@Query("select a from PlanMasterEntity a where  UPPER(a.planName) =:planName and a.id != :id")
	PlanMasterEntity findByPlanNameNotInId(@Param("planName") String planName, @Param("id") UUID id);
	
	@Query(nativeQuery = true, value = "select p.* from plan_master p where  p.priority > :priority order by p.priority desc limit 1")
	PlanMasterEntity getHigherAmountPlanByPriority(@Param("priority") Integer priority);
	
	@Query(nativeQuery = true, value = "select p.* from plan_master p where  p.priority < :priority order by p.priority desc limit 1")
	PlanMasterEntity getLowerAmountPlanByPriority(@Param("priority") Integer priority);

	@Query("select max(p.priority) from PlanMasterEntity p ")
	Integer getMaxPriority();
	
	@Query("select p from PlanMasterEntity p where  UPPER(p.planName) =:planName ")
	PlanMasterEntity findByPlanName(@Param("planName") String planName);
	
	@Query("select p from PlanMasterEntity p where  p.priority = :priority ")
	PlanMasterEntity getPlanByPriority(@Param("priority") Integer priority);
	
}
