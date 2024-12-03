package com.oasys.uppcl_user__master_management.controller;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.constant.PrivilegeConstant;
import com.oasys.uppcl_user__master_management.dto.ServiceTypeDTO;
import com.oasys.uppcl_user__master_management.service.ServiceTypeService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/servicetype")
@Log4j2
public class ServiceTypeController {
	
	@Autowired
	ServiceTypeService serviceTypeService;

	//@PreAuthorize(PrivilegeConstant.ADD_SERVICE_TYPE)
	@PostMapping("/create")
	public BaseDTO createServiceType(@Valid @RequestBody ServiceTypeDTO serviceTypeDTO) {
		//log.info("<--- ServiceType create Controller STARTED --->");
		BaseDTO response = serviceTypeService.create(serviceTypeDTO);
		//log.info("<--- ServiceType create Controller END --->");
		return response;
	}

	//@PreAuthorize("#oauth2.hasScope('Admin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/getall")
	public BaseDTO getAll() {
		//log.info("<--- ServiceType getAll Controller STARTED --->");
		BaseDTO response = serviceTypeService.getAll();
		//log.info("<--- ServiceType getAll Controller END --->");
		return response;
	}
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/getallactive")
	public BaseDTO getAllActive() {
		//log.info("<--- ServiceType getAll Controller STARTED --->");
		BaseDTO response = serviceTypeService.getAllActive();
		//log.info("<--- ServiceType getAll Controller END --->");
		return response;
	}
	
	
	//@PreAuthorize(PrivilegeConstant.UPDATE_SERVICE_TYPE)
	@PutMapping("/update/{id}")
	public BaseDTO updateServiceType(@PathVariable("id") UUID id,@Valid @RequestBody ServiceTypeDTO serviceTypeDTO) {
		//log.info("<--- ServiceType update Controller STARTED --->");
		BaseDTO response = serviceTypeService.update(id,serviceTypeDTO);
		//log.info("<--- ServiceType update Controller END --->");
		return response;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@PostMapping("/lazylist")
	public BaseDTO getLazyList(@RequestBody PaginationRequestDTO requestData) {
	BaseDTO response = new BaseDTO();
	//log.info("<--- ServiceType lazylist Controller STARTED --->");
	response = serviceTypeService.getLazyList(requestData);
	//log.info("<--- ServiceType lazylist Controller END --->");
	return response;
	}

	//@PreAuthorize("#oauth2.hasScope('Admin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/get/{id}")
	public BaseDTO getById(@PathVariable("id") UUID id) {
		//log.info("<--- ServiceType getById Controller STARTED --->");
		BaseDTO response = serviceTypeService.getById(id);
		//log.info("<--- ServiceType getById Controller END --->");
		return response;
	}

	//@PreAuthorize("#oauth2.hasScope('Admin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@DeleteMapping("/delete/{id}")
	public BaseDTO deleteServiceType(@PathVariable("id") UUID id) {
		//log.info("<--- ServiceType delete Controller STARTED --->");
		BaseDTO response = serviceTypeService.delete(id);
		//log.info("<--- ServiceType delete Controller END --->");
		return response;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@PutMapping("/softdelete/{id}")
	public BaseDTO softDelete(@PathVariable("id") UUID id) {
		//log.info("<--- ServiceType delete Controller STARTED --->");
		BaseDTO response = serviceTypeService.softDelete(id);
		//log.info("<--- ServiceType delete Controller END --->");
		return response;
	}
	

	//@PreAuthorize("#oauth2.hasScope('Admin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/list")
	public BaseDTO list() {
		//log.info("<--- ServiceType list Controller STARTED --->");
		BaseDTO response = serviceTypeService.fetchServiceTypeWithServiceList();
		//log.info("<--- ServiceType list Controller END --->");
		return response;
	}

}
