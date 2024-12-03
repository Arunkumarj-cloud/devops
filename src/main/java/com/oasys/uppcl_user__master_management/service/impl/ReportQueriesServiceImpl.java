
package com.oasys.uppcl_user__master_management.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oasys.config.BaseDTO;
import com.oasys.config.ErrorDescription;
import com.oasys.uppcl_user__master_management.dao.ReportQueriesDao;
import com.oasys.uppcl_user__master_management.dto.ReportQueriesDTO;
import com.oasys.uppcl_user__master_management.entity.ReportQueriesEntity;
import com.oasys.uppcl_user__master_management.service.ReportQueriesService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ReportQueriesServiceImpl implements ReportQueriesService {
	
	@Autowired
	ReportQueriesDao reportQueriesDao;
	
	@Autowired
	ObjectMapper mapper;

	public BaseDTO getByReportQueryName(ReportQueriesDTO name) {
		//log.info(" <----- ReportQueries getByReportQueryName Dao STARTED -----> ");	
		BaseDTO response = new BaseDTO();
		try {
			ReportQueriesEntity query = reportQueriesDao.getByReportQueryName(name);
			ReportQueriesDTO reportQeuriesDTO = mapper.convertValue(query, ReportQueriesDTO.class);
			if(reportQeuriesDTO.getReportName().isEmpty()) {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.error("No Record Found..");
			}else {
				response.setResponseContent(reportQeuriesDTO);
				response.setMessage("Successfully Get ReportQueries Details..");
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Get ReportQueries Details..");
			}
		} catch (Exception e) {
			log.error("<---- ReportQueriesDaoImpl.getByReportQueryName() ----> EXCEPTION", e);
		}
		//log.info(" <----- ReportQueries getByReportQueryName Dao END -----> ");
		return response;
	}
	
	public BaseDTO  getReportbyName(String name) {
	//log.info(" <----- ReportQueries getByReportQueryName Dao STARTED -----> ");	
	BaseDTO response = new BaseDTO();
	try {
		ReportQueriesEntity query = reportQueriesDao.getReportbyName(name);
		ReportQueriesDTO reportQeuriesDTO = mapper.convertValue(query, ReportQueriesDTO.class);
		if(reportQeuriesDTO.getReportName().isEmpty()) {
			response.setMessage("No Record Found..");
			response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
			//log.error("No Record Found..");
		}else {
			response.setResponseContent(reportQeuriesDTO);
			response.setMessage("Successfully Get ReportQueries Details..");
			response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
			//log.info("Successfully Get ReportQueries Details..");
		}
	} catch (Exception e) {
		log.error("<---- ReportQueriesDaoImpl.getByReportQueryName() ----> EXCEPTION", e);
	}
	//log.info(" <----- ReportQueries getByReportQueryName Dao END -----> ");
	return response;
	}
}
