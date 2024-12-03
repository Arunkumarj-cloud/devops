package com.oasys.uppcl_user__master_management.controller;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oasys.config.BaseDTO;
import com.oasys.constant.PrivilegeConstant;
import com.oasys.uppcl_user__master_management.dto.PlanServiceMappingRequestDTO;
import com.oasys.uppcl_user__master_management.service.PlanServiceMappingService;

import io.swagger.annotations.ApiOperation;
@RestController
@RequestMapping("/plan-service-mapping")
public class PlanServiceMappingController{
	@Autowired
  PlanServiceMappingService planServiceMappingService;

//@PreAuthorize(PrivilegeConstant.ADD_PLAN_SERVICES_MAPPING)
@ApiOperation(value = "API to add plan service mapping ", response = BaseDTO.class)
@PostMapping
public BaseDTO save(@Valid @RequestBody PlanServiceMappingRequestDTO requestDTO) {
	return planServiceMappingService.save(requestDTO);
}

//@PreAuthorize(PrivilegeConstant.UPDATE_PLAN_SERVICES_MAPPING)
@ApiOperation(value = "API to update plan service mapping ", response = BaseDTO.class)
@PutMapping("/update")
public BaseDTO update(@Valid @RequestBody PlanServiceMappingRequestDTO requestDTO) {
	return planServiceMappingService.update(requestDTO);
}

//@PreAuthorize(PrivilegeConstant.UPDATE_PLAN_SERVICES_MAPPING)
@ApiOperation(value = "API to update plan service mapping by planId and serviceId", response = BaseDTO.class)
@PutMapping
public BaseDTO update(@RequestParam(value = "planId", required = true) String planId,
		@RequestParam(value = "serviceId", required = true) String serviceId) {
	return planServiceMappingService.update(planId, serviceId);
}

//@PreAuthorize(PrivilegeConstant.VIEW_PLAN_SERVICES_MAPPING_BY_PLAN_ID)
@ApiOperation(value = "API to get all mapped services by plan id", response = BaseDTO.class)
@GetMapping
public BaseDTO getByPlanId(@RequestParam(value = "planId", required = true) UUID planId) {
	return planServiceMappingService.getByPlanId(planId);
}

//@PreAuthorize(PrivilegeConstant.VIEW_ALL_PLAN_SERVICES_MAPPING)
@ApiOperation(value = "API to get all plan services mapping records", response = BaseDTO.class)
@GetMapping("/lazylist")
public BaseDTO getAll() {
	return planServiceMappingService.getAll();
}

//@PreAuthorize(PrivilegeConstant.DELETE_PLAN_SERVICES_MAPPING)
@ApiOperation(value = "API to delete  mapped services by plan id and service id", response = BaseDTO.class)
@DeleteMapping
public BaseDTO delete(@RequestParam(value = "planId", required = true) UUID planId,
		@RequestParam(value = "serviceId", required = true) UUID serviceId) {
	return planServiceMappingService.deleteByPlanIdAndServiceId(planId, serviceId);
}
}

