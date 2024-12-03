package com.oasys.uppcl_user__master_management.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oasys.uppcl_user__master_management.entity.ReportQueriesEntity;

public interface ReportQueriesRepository extends JpaRepository<ReportQueriesEntity, UUID> {
	
	@Query("select a from ReportQueriesEntity a where a.reportName=:id")
	ReportQueriesEntity getReportbyName(@Param("id") String name);
	
	
	
	ReportQueriesEntity findByReportNameIgnoreCase(String reportName);

}
