package com.oasys.uppcl_user__master_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oasys.config.BaseDTO;
import com.oasys.uppcl_user__master_management.service.CommissionHierarchyService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/hierarchy")
@Log4j2
public class CommissionHierarchyController {

	@Autowired
	CommissionHierarchyService commissionHierarchyService;
	
	//@PreAuthorize(PrivilegeConstant.VIEW_HIERARCHY_NAME)
	@GetMapping("/all")
	public BaseDTO getAllActive(@RequestParam(value = "status", required = false) Boolean status) {
		BaseDTO response = commissionHierarchyService.getAllActive(status);
		return response;
	}
	
}
