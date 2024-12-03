package com.oasys.uppcl_user__master_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oasys.config.BaseDTO;
import com.oasys.uppcl_user__master_management.service.PlanCountService;


	@RestController
	@RequestMapping("/pancard")
	public class PancardRejectionController {
		@Autowired(required=true)
		PlanCountService planCountService;
		@GetMapping("/getpancardist")
		public BaseDTO getRejectionPanCardList()  {
			BaseDTO response = new BaseDTO();
			response = planCountService.getRejectionPanCardList();
			return response;
		}

}