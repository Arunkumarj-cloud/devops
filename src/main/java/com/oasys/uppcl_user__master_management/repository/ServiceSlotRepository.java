package com.oasys.uppcl_user__master_management.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oasys.uppcl_user__master_management.entity.ServiceSlotEntity;

public interface ServiceSlotRepository extends JpaRepository<ServiceSlotEntity, UUID> {

	@Query("select a from ServiceSlotEntity a ORDER by a.startRange")
	List<ServiceSlotEntity> getAllActive();

	@Query("select a from ServiceSlotEntity a where a.startRange=:startRange and a.endRange=:endRange")
	List<ServiceSlotEntity> checkCode(@Param("startRange") Long startRange,@Param("endRange") Long endRange);
	
	/*
	 * @Query("select a from ServiceSlotEntity a where a.startRange=:startRange and a.endRange=:endRange and a.serviceCategoryId.id =:servicecategoryid"
	 * ) Optional<ServiceSlotEntity> check(@Param("startRange") Long
	 * startRange,@Param("endRange") Long endRange ,@Param("servicecategoryid") UUID
	 * servicecategoryid);
	 */
	
	
	@Query("select a from ServiceSlotEntity a where a.startRange=:startRange and a.endRange=:endRange and a.serviceCategoryId.id =:servicecategoryid")
	Optional<ServiceSlotEntity> check(@Param("startRange") Long startRange,@Param("endRange") Long endRange ,@Param("servicecategoryid") UUID servicecategoryid);
	

	@Query("select a from ServiceSlotEntity a where a.status = true and a.subCategoryId.id =:servicecategoryid ORDER by a.startRange ASC")
	List<ServiceSlotEntity> findByCategoryId(@Param("servicecategoryid") UUID servicecategoryid);
	
	@Query("select a from ServiceSlotEntity a where a.status=true and a.startRange<=:amount and a.endRange>=:amount and a.subCategoryId.id =:servicecategoryid")
	Optional<ServiceSlotEntity> getSlot(@Param("amount") Long amount,@Param("servicecategoryid") UUID servicecategoryid);


	@Query("select a from ServiceSlotEntity a where a.status=true and a.startRange<=:amount and a.endRange>=:amount ")
	List<ServiceSlotEntity> getSlot(@Param("amount") Long amount);
	
	
	@Query("select a from ServiceSlotEntity a where a.status=true and ( a.startRange<= :startRange and a.endRange>= :startRange or a.startRange<= :endRange and a.endRange>= :endRange)  and a.subCategoryId.id =:subCategoryId")
	List<ServiceSlotEntity> isSlotRangePresnt(@Param("startRange") Long startRange,@Param("endRange") Long endRange,@Param("subCategoryId") UUID servicecategoryid);
}

