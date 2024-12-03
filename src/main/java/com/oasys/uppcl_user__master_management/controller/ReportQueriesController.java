package com.oasys.uppcl_user__master_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oasys.config.BaseDTO;
import com.oasys.uppcl_user__master_management.dto.ReportQueriesDTO;
import com.oasys.uppcl_user__master_management.service.ReportQueriesService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/reportquery")
@Log4j2
public class ReportQueriesController {
	
	@Autowired
	ReportQueriesService reportQueriesService;
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@GetMapping("/get")
	public BaseDTO getByReportQueryName(@RequestBody ReportQueriesDTO name) {
		//log.info("<--- ReportQueries getByReportQueryName Controller STARTED --->");
		BaseDTO response = reportQueriesService.getByReportQueryName(name);
		//log.info("<--- ReportQueries getByReportQueryName Controller END --->");
		return response;
	}
	
	@GetMapping("/getReportbyName/{name}")
	public BaseDTO getReportbyName(@PathVariable String name) {
		
				//log.info("<--- ReportQueries getByReporByName Controller STARTED --->");
				BaseDTO response = reportQueriesService.getReportbyName(name);
				//log.info("<--- ReportQueries getByReportByName Controller END --->");
				return response;
		
	}
	

}
