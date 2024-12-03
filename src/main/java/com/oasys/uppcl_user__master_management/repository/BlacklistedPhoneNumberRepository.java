package com.oasys.uppcl_user__master_management.repository;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oasys.uppcl_user__master_management.entity.BlacklistedPhoneNumberEntity;

public interface BlacklistedPhoneNumberRepository extends JpaRepository<BlacklistedPhoneNumberEntity, UUID> {


	Boolean existsByPhoneNumber(@Param("phoneNumber") String phoneNumber);

	Optional<BlacklistedPhoneNumberEntity> findByPhoneNumber(String phoneNumber);

	@Query("SELECT a FROM BlacklistedPhoneNumberEntity a ")
	Page<BlacklistedPhoneNumberEntity> getReport(Pageable pageable);

	@Query("SELECT a FROM BlacklistedPhoneNumberEntity a where  a.createdDate BETWEEN :fromDate AND :toDate  ")
	Page<BlacklistedPhoneNumberEntity> getReportwithDate(Pageable pageable, @Param("fromDate") Date fromDate,
			@Param("toDate") Date toDate);

	@Query("SELECT a FROM BlacklistedPhoneNumberEntity a where (UPPER(a.phoneNumber) LIKE %:search%)")
	Page<BlacklistedPhoneNumberEntity> getReportSearch(@Param("search") String search, Pageable pageable);

	@Query("SELECT a FROM BlacklistedPhoneNumberEntity a where (UPPER(a.phoneNumber) LIKE %:search%  ) AND  (a.createdDate BETWEEN :fromDate AND :toDate) ")
	Page<BlacklistedPhoneNumberEntity> getReportSearchwithDate(@Param("search") String search, Pageable pageable,
			@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);


}
