package com.oasys.uppcl_user__master_management.controller;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.ProjectTypeRequestDTO;
import com.oasys.uppcl_user__master_management.service.ProjectTypeService;

import io.swagger.annotations.ApiOperation;
@Component
@RestController
@RequestMapping("/project-type")
public class ProjectTypeController {
	@Autowired(required=true)
	private ProjectTypeService projectTypeService;
	
	//@ApiOperation(value = "API to add project type record for user", response = BaseDTO.class)
	@PostMapping("/ptpo")
//	@PreAuthorize(PrivilegeConstant.ADD_PROJECT_TYPE)
	public BaseDTO save(@RequestBody @Valid ProjectTypeRequestDTO requestDTO) {
		return projectTypeService.add(requestDTO);
	}
	
	//@ApiOperation(value = "API to update existing project type record for user", response = BaseDTO.class)
	@PutMapping("/ptpm")
//	@PreAuthorize(PrivilegeConstant.UPDATE_PROJECT_TYPE)
	public BaseDTO update(@RequestBody @Valid ProjectTypeRequestDTO requestDTO) {
		return projectTypeService.update(requestDTO);
	}
	
	
	@ApiOperation(value = "API to get project type listing", response = BaseDTO.class)
	@PostMapping("/lazyList")
//	@PreAuthorize(PrivilegeConstant.PROJECT_TYPE_LIST)
	public BaseDTO getLazyList(@RequestBody PaginationRequestDTO requestData) {
		return projectTypeService.getBySearchFilter(requestData);

	}
	
	@ApiOperation(value = "API to get project type list by id", notes = "Returns HTTP 200 if successfully gets the record")
	@GetMapping("/{id}")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	public BaseDTO getById(@PathVariable UUID id) {
		return projectTypeService.getById(id);
	}
	
	@ApiOperation(value = "API to get project type record for distributor", notes = "Returns HTTP 200 if successfully gets the record")
	@GetMapping("/getDistributor")
//	@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	public BaseDTO getDistributor(@RequestParam(required=true, value="roleId") UUID roleId, @RequestParam(required=false, value="userTypeId") UUID userTypeId) {
		return projectTypeService.getDistributor(roleId, userTypeId);
	}
	
	@ApiOperation(value = "API to get all active project types", notes = "Returns HTTP 200 if successfully gets the record")
	@GetMapping("/all/active")
//	@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	public BaseDTO getAllActive() {
		return projectTypeService.getAllActive();
	}
	
	@ApiOperation(value = "API to get project type by name", notes = "Returns HTTP 200 if successfully gets the record")
	@GetMapping("/by/name/{name}")
//	@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	public BaseDTO getByName(@PathVariable String name) {
		return projectTypeService.getByName(name);
	}
	
	@ApiOperation(value = "API to get all project types", notes = "Returns HTTP 200 if successfully gets the record")
	@GetMapping("/all")
//	@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	public BaseDTO getAll() {
		return projectTypeService.getAll();
	}

}
