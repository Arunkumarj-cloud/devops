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
import com.oasys.uppcl_user__master_management.dto.GenderMasterDTO;
import com.oasys.uppcl_user__master_management.service.GenderMasterService;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping("/gender")
public class GenderMasterController {
	@Autowired
	GenderMasterService genderMasterService;

	//@PreAuthorize(PrivilegeConstant.ADD_GENDER)
	@PostMapping("/create")
	public BaseDTO create(@Valid @RequestBody GenderMasterDTO genderMasterDTO) {
		//log.info("<== Started GenderMasterController.create() ==>");
		BaseDTO response = new BaseDTO();
		response = genderMasterService.create(genderMasterDTO);
		//log.info("<== Ended GenderMasterController.create() ==>");
		return response;

	}

	//@PreAuthorize(PrivilegeConstant.UPDATE_GENDER)
	@PutMapping("/update/{id}")
	public BaseDTO update(@PathVariable UUID id, @Valid @RequestBody GenderMasterDTO genderMasterDTO) {
		//log.info("<== Started GenderMasterController.update() ==>");
		BaseDTO response = new BaseDTO();
		response = genderMasterService.update(id,genderMasterDTO);
		//log.info("<== Ended GenderMasterController.update() ==>");
		return response;

	}
   
	 //@PreAuthorize("#oauth2.hasScope('Admin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	 @PostMapping("/lazylist")
	 public BaseDTO getLazyList(@RequestBody PaginationRequestDTO requestData) {
	 // log.info("<== Started GenderMasterController.create() ==>"); 
	  BaseDTO response= new BaseDTO();
	  response = genderMasterService.getAllLazy(requestData);
	 // log.info("<== Ended GenderMasterController.getLazyList() ==>"); 
	  return response;  
	  
	  }

   // @PreAuthorize("#oauth2.hasScope('Admin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/get/{id}")
	public BaseDTO getById(@PathVariable UUID id) {
		//log.info("<== Started GenderMasterController.getById() ==>");
		BaseDTO response = new BaseDTO();
		response = genderMasterService.getById(id);
		//log.info("<== Ended GenderMasterController.getById() ==>");
		return response;

	}
	
    //@PreAuthorize("#oauth2.hasScope('Admin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/getAll")
	public BaseDTO getAll() {
		//log.info("<== Started GenderMasterController.getAll() ==>");
		BaseDTO response = new BaseDTO();
		response = genderMasterService.getAll();
		//log.info("<== Ended GenderMasterController.getAll() ==>");
		return response;

	}
		
    
    //@PreAuthorize("#oauth2.hasScope('Admin') or #oauth2.hasScope('Sales Officer') or #oauth2.hasScope('Customer Support Officer') or #oauth2.hasScope('TenantAdmin') or #oauth2.hasScope('Finance Officer') or"
    //		+ " #oauth2.hasScope('TenantAdmin') or #oauth2.hasScope('Third Party') or #oauth2.hasScope('districtDistributors') or #oauth2.hasScope('pincodeDistributors') or #oauth2.hasScope('SuperPincodeDistributor')  or #oauth2.hasScope('SuperDistrictDistributor') or #oauth2.hasScope('KYC Admin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/getallactive")
	public BaseDTO getAllActive() {
		//log.info("<== Started GenderMasterController.getAllActive() ==>");
		BaseDTO response = genderMasterService.getAllActive();
		//log.info("<== Ended GenderMasterController.getAllActive() ==>");
		return response;
	}
	
    //@PreAuthorize("#oauth2.hasScope('Admin')")
	@DeleteMapping("/softdelete/{id}")
	public BaseDTO softDelete(@PathVariable("id") UUID id) {
		//log.info("<--- Started GenderMasterController  softDelete  STARTED --->");
		BaseDTO baseDTO = genderMasterService.softDelete(id);
		//log.info("<---  GenderMasterController  softDelete  END --->");
		return baseDTO;
	}

}
