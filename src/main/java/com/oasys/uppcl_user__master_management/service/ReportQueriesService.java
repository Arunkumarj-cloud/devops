package com.oasys.uppcl_user__master_management.service;

import com.oasys.config.BaseDTO;
import com.oasys.uppcl_user__master_management.dto.ReportQueriesDTO;

public interface ReportQueriesService {
	
	BaseDTO getByReportQueryName(ReportQueriesDTO name);
	BaseDTO getReportbyName(String name);
}
