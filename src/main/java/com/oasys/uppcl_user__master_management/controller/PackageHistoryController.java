package com.oasys.uppcl_user__master_management.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.service.PackageHistoryService;

@RestController
@RequestMapping("/package-version-history")
public class PackageHistoryController {
	@Autowired(required=true)
	private PackageHistoryService packageHistoryService;

//	@PreAuthorize(PrivilegeConstant.VIEW_PACKAGE_VERSION_HISTORY)
	@PostMapping("/list")
	public BaseDTO getBySearchFilter(@RequestBody PaginationRequestDTO requestDTO) {
		return packageHistoryService.getBySearchFilter(requestDTO);
	}
}
