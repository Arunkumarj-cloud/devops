package com.oasys.uppcl_user__master_management.dao;

import com.oasys.config.BaseDTO;
import com.oasys.uppcl_user__master_management.dto.ReportQueriesDTO;
import com.oasys.uppcl_user__master_management.entity.ReportQueriesEntity;

public interface ReportQueriesDao {
	
	ReportQueriesEntity getByReportQueryName(ReportQueriesDTO name);
	ReportQueriesEntity getReportbyName(String name);
}
