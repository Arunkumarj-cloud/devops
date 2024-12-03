package com.oasys.uppcl_user__master_management.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class ReportQueriesDTO {

	UUID id;
	String reportName;
	String dataQuery;
	String description;

}
