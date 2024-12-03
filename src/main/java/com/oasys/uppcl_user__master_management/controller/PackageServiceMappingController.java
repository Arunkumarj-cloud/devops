package com.oasys.uppcl_user__master_management.controller;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.PackageRequestDTO;
import com.oasys.uppcl_user__master_management.service.PackageService;


@RestController
@RequestMapping("/package-service-mapping")
public class PackageServiceMappingController {
	@Autowired
	PackageService packageService;

//	@PreAuthorize(PrivilegeConstant.ADD_PACKAGE_SERVICE_MAPPING)
	@PostMapping("/pspost")
	public BaseDTO save(@Valid @RequestBody PackageRequestDTO requestDTO) {
		return packageService.save(requestDTO);
	}
	
//	@PreAuthorize(PrivilegeConstant.EDIT_PACKAGE_SERVICE_MAPPING)
	@PutMapping("/psput")
	public BaseDTO update(@Valid @RequestBody PackageRequestDTO requestDTO) {
		return packageService.update(requestDTO);
	}
	
//	@PreAuthorize(PrivilegeConstant.PACKAGE_SERVICE_MAPPING_LIST)
	@PostMapping("/list")
	public BaseDTO getBySearchFilter(@RequestBody PaginationRequestDTO requestDTO) {
		return packageService.getBySearchFilter(requestDTO);
	}
	

//	@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/psget")
	public BaseDTO getByServiceCategoryId(@RequestParam(value="packageId", required=true) UUID  packageId) {
		return packageService.getByPackageId(packageId);
	}
	
//	@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/default")
	public BaseDTO getByServiceCategoryId() {
		return packageService.getDefaultPackage();
	}

}
