package com.oasys.uppcl_user__master_management.repository;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oasys.uppcl_user__master_management.entity.BlacklistedPANNumberEntity;

public interface BlacklistedPANNumberRepository extends JpaRepository<BlacklistedPANNumberEntity, UUID>  {

	Boolean existsByPanNumberIgnoreCase(@Param("panNumber") String phoneNumber);

	Optional<BlacklistedPANNumberEntity> findByPanNumberIgnoreCase(String panNumber);

	@Query("SELECT a FROM BlacklistedPANNumberEntity a ")
	Page<BlacklistedPANNumberEntity> getReport(Pageable pageable);

	@Query("SELECT a FROM BlacklistedPANNumberEntity a where  a.createdDate BETWEEN :fromDate AND :toDate  ")
	Page<BlacklistedPANNumberEntity> getReportwithDate(Pageable pageable, @Param("fromDate") Date fromDate,
			@Param("toDate") Date toDate);

	@Query("SELECT a FROM BlacklistedPANNumberEntity a where (UPPER(a.panNumber) LIKE %:search%)")
	Page<BlacklistedPANNumberEntity> getReportSearch(@Param("search") String search, Pageable pageable);

	@Query("SELECT a FROM BlacklistedPANNumberEntity a where (UPPER(a.panNumber) LIKE %:search%  ) AND  (a.createdDate BETWEEN :fromDate AND :toDate) ")
	Page<BlacklistedPANNumberEntity> getReportSearchwithDate(@Param("search") String search, Pageable pageable,
			@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);


}
