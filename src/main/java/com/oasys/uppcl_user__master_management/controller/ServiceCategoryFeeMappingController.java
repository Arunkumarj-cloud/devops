package com.oasys.uppcl_user__master_management.controller;

import java.util.List;
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
import com.oasys.constant.PrivilegeConstant;
import com.oasys.uppcl_user__master_management.dto.ServiceCategoryFeeMappingRequestDTO;
import com.oasys.uppcl_user__master_management.service.ServiceCategoryFeeMappingService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/service-category-fee-mapping")
public class ServiceCategoryFeeMappingController {
	@Autowired
	private ServiceCategoryFeeMappingService serviceFeeMappingService;
	
	@ApiOperation(value = "API to service category fee mapping record", response = BaseDTO.class)
	@PostMapping()
	//@PreAuthorize(PrivilegeConstant.ADD_SERVICE_FEE_MAPPING)
	public BaseDTO save(@RequestBody @Valid ServiceCategoryFeeMappingRequestDTO requestDTO) {
		return serviceFeeMappingService.save(requestDTO);
	}
	
	@ApiOperation(value = "API to update service category fee mapping record", response = BaseDTO.class)
	@PutMapping()
	//@PreAuthorize(PrivilegeConstant.UPDATE_SERVICE_FEE_MAPPING)
	public BaseDTO update(@RequestBody @Valid ServiceCategoryFeeMappingRequestDTO requestDTO) {
		return serviceFeeMappingService.update(requestDTO);
	}
	
	
	@ApiOperation(value = "API to get service category fee mapping list by filter", response = BaseDTO.class)
	@PostMapping("/list")
	//@PreAuthorize(PrivilegeConstant.SERVICE_FEE_MAPPING_LIST)
	public BaseDTO getLazyList(@RequestBody PaginationRequestDTO requestData) {
		return serviceFeeMappingService.getBySearchFilter(requestData);

	}
	
	@ApiOperation(value = "API to get service category fee mapping record by service category id", notes = "Returns HTTP 200 if successfully gets the record")
	@GetMapping
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	public BaseDTO getById(@RequestParam(value="serviceCategoryId", required=true) String serviceCategoryId) {
		return serviceFeeMappingService.getByServiceId(serviceCategoryId);
	}
	
	@ApiOperation(value = "API to get sum of configured amount in given service category ids", notes = "Returns HTTP 200 if successfully gets the record")
	@GetMapping("/by/serviceCategoryIds")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	public BaseDTO getTotalAmountByServiceCategoryIds(
			@RequestParam("serviceCategoryIds") List<UUID> serviceCategoryIds) {
		return serviceFeeMappingService.getTotalAmountByServiceCategoryIds(serviceCategoryIds);
	}
	
	@ApiOperation(value = "API to get all service category fee mapping records", notes = "Returns HTTP 200 if successfully gets the record")
	@GetMapping("/all")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	public BaseDTO getAll(@RequestParam(value="status", required=false) Boolean status) {
		return serviceFeeMappingService.getAllRecords(status);
	}

}
