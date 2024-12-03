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
import com.oasys.uppcl_user__master_management.dto.PrivacyPolicyDTO;
import com.oasys.uppcl_user__master_management.service.PrivacyPolicyService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/privacypolicy")
@Log4j2

public class PrivacyPolicyController {
	
	@Autowired
	PrivacyPolicyService privacyPolicyService;
	
	//@PreAuthorize(PrivilegeConstant.ADD_PRIVACY_POLICY)
	@PostMapping("/create")
	public BaseDTO create(@Valid @RequestBody PrivacyPolicyDTO privacyPolicyDTO) {
		//log.info("<== Started PrivacyPolicyController.create ==>");
		BaseDTO baseDTO = new BaseDTO();
		baseDTO = privacyPolicyService.create(privacyPolicyDTO);
		//log.info("<== Ended PrivacyPolicyController.create ==>");
		return baseDTO;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/getall")
	public BaseDTO getAll() {
		//log.info("<--- Privacy Policy getAll Controller STARTED --->");
		BaseDTO baseDTO = privacyPolicyService.getAll();
		//log.info("<--- Privacy Policy getAll Controller END --->");
		return baseDTO;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@GetMapping("/getallactive")
	public BaseDTO getAllActive() {
		//log.info("<--- Privacy Policy getAll active Controller STARTED --->");
		BaseDTO baseDTO = privacyPolicyService.getAllActive();
		//log.info("<--- Privacy Policy getAll active Controller END --->");
		return baseDTO;
	}
	
	//@PreAuthorize(PrivilegeConstant.UPDATE_PRIVACY_POLICY)
	@PutMapping("/update/{id}")
	public BaseDTO update(@PathVariable("id") UUID id, @Valid @RequestBody PrivacyPolicyDTO privacyPolicyDTO) {
		//log.info("<--- Privacy Policy update Controller STARTED --->");
		BaseDTO baseDTO = privacyPolicyService.update(id, privacyPolicyDTO);
		//log.info("<--- Privacy Policy update Controller END --->");
		return baseDTO;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
//	@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@PostMapping("/lazylist")
	public BaseDTO getLazyList(@RequestBody PaginationRequestDTO requestData) {
		BaseDTO baseDTO = new BaseDTO();
		//log.info("<--- Privacy Policy lazylist Controller STARTED --->");
		baseDTO = privacyPolicyService.getLazyList(requestData);
		//log.info("<--- Privacy Policy lazylist Controller END --->");
		return baseDTO;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/get/{id}")
	public BaseDTO getById(@PathVariable("id") UUID id) {
		//log.info("<--- Privacy Policy getById Controller STARTED --->");
		BaseDTO baseDTO = privacyPolicyService.getById(id);
		//log.info("<--- Privacy Policy getById Controller END --->");
		return baseDTO;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@DeleteMapping("/delete/{id}")
	public BaseDTO delete(@PathVariable("id") UUID id) {
		//log.info("<--- Privacy Policy delete Controller STARTED --->");
		BaseDTO baseDTO = privacyPolicyService.delete(id);
		//log.info("<--- Privacy Policy delete Controller END --->");
		return baseDTO;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@PutMapping("/softdelete/{id}")
	public BaseDTO softDeleteById(@PathVariable UUID id) {
		//log.info("<== Started PlanMasterController.softDeleteById ==>");
		BaseDTO baseDTO = privacyPolicyService.softDelete(id);
		//log.info("<== Ended PlanMasterController.softDeleteById ==>");
		return baseDTO;
	}
}
