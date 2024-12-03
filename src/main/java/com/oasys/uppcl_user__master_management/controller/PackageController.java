package com.oasys.uppcl_user__master_management.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oasys.config.BaseDTO;
import com.oasys.constant.PrivilegeConstant;
import com.oasys.uppcl_user__master_management.service.PackageService;


@RestController
@RequestMapping("/package-master")
public class PackageController {

	@Autowired
	PackageService packageService;

	@GetMapping("/all")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	public BaseDTO getByServiceCategoryId(@RequestParam(value = "status", required = false) Boolean status) {
		return packageService.getAllPackageListByStatus(status);
	}
	
}
