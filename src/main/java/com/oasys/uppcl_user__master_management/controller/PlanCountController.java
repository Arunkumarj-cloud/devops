package com.oasys.uppcl_user__master_management.controller;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.oasys.config.BaseDTO;
import com.oasys.uppcl_user__master_management.service.PlanCountService;


@RestController
@RequestMapping("/plancount")
public class PlanCountController {
	
	@Autowired(required=true)
	PlanCountService planCountService;
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
//	@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@PostMapping("/getPlanCount")
	public BaseDTO getPlanCount() {
		BaseDTO response = new BaseDTO();
			//log.info(" <----- Admin Dashboard Count Details -----> ");
			Map<String, Integer> agentCounts = planCountService.getPlanCount();
			response.setResponseContent(agentCounts);
			response.setMessage("success...");
//			response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
			//log.info(" <----- success -----> ");
		
		return response;
	}

	//@PreAuthorize("#oauth2.hasScope('Admin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@PostMapping("/getservicecount")
	public BaseDTO getServiceCount() {
		BaseDTO response = new BaseDTO();
			//log.info(" <----- Admin Dashboard Count Details -----> ");
			Map<String, Integer> agentCounts = planCountService.getServiceCount();
			response.setResponseContent(agentCounts);
			response.setMessage("success...");
//			response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
			//log.info(" <----- success -----> ");
		
		return response;
	}
}
