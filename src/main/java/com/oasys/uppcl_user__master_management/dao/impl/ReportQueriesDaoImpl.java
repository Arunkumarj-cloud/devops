package com.oasys.uppcl_user__master_management.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.oasys.config.BaseDTO;
import com.oasys.uppcl_user__master_management.dao.ReportQueriesDao;
import com.oasys.uppcl_user__master_management.dto.ReportQueriesDTO;
import com.oasys.uppcl_user__master_management.entity.ReportQueriesEntity;
import com.oasys.uppcl_user__master_management.repository.ReportQueriesRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Repository
public class ReportQueriesDaoImpl implements ReportQueriesDao {
	
	@Autowired
	ReportQueriesRepository repository;
	
	public ReportQueriesEntity getByReportQueryName(ReportQueriesDTO name) {
		//log.info(" <----- ReportQueries getByReportQueryName Dao STARTED -----> ");
		ReportQueriesEntity query = new ReportQueriesEntity();	
		try {
			query = repository.findByReportNameIgnoreCase(name.getReportName());

		} catch (Exception e) {
			log.error("<---- ReportQueriesDaoImpl.getByReportQueryName() ----> EXCEPTION", e);
		}
		//log.info(" <----- ReportQueries getByReportQueryName Dao END -----> ");
		return query;
	}

	public ReportQueriesEntity getReportbyName(String name) {
		//log.info(" <----- ReportQueries getByReportQueryName Dao STARTED -----> ");
		ReportQueriesEntity query = new ReportQueriesEntity();	

		try {
			query = repository.getReportbyName(name);
			

		} catch (Exception e) {
			log.error("<---- ReportQueriesDaoImpl.getByReportQueryName() ----> EXCEPTION", e);
		}
		//log.info(" <----- ReportQueries getByReportQueryName Dao END -----> ");
		return query;
	}

}
