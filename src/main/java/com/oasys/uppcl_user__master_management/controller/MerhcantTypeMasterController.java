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
import com.oasys.uppcl_user__master_management.dto.MerchantTypeMasterDTO;
import com.oasys.uppcl_user__master_management.service.MerchantTypeMasterService;

import lombok.extern.log4j.Log4j2;
@RestController
@RequestMapping("/merchanttype")
@Log4j2
public class MerhcantTypeMasterController  {
	@Autowired
	MerchantTypeMasterService merchantTypeMasterService;

	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@GetMapping("/getAll")
	public BaseDTO getAll() {
		//log.info("<== Started MerhcantTypeController.getAll() ==>");
		BaseDTO response = new BaseDTO();
		response = merchantTypeMasterService.getAll();
		//log.info("<== Ended MerhcantTypeController.getAll() ==>");
		return response;
	}

	//@PreAuthorize(PrivilegeConstant.ADD_USER_TYPE)
	@PostMapping("/create")
	public BaseDTO create(@Valid @RequestBody MerchantTypeMasterDTO merchantTypeDTO) {
		//log.info("<== Started MerhcantTypeController.addMerchantType ==>");
		BaseDTO response = new BaseDTO();
		response = merchantTypeMasterService.create(merchantTypeDTO);
		//log.info("<== Ended MerhcantTypeController.addMerchantType ==>");
		return response;
	}

	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@DeleteMapping("/delete/{id}")
	public BaseDTO delete(@PathVariable UUID id) {
		//log.info("<== Started MerhcantTypeController.delete ==>");
		BaseDTO response = new BaseDTO();
		response = merchantTypeMasterService.delete(id);
		//log.info("<== Ended MerhcantTypeController.delete ==>");
		return response;
	}

	//@PreAuthorize(PrivilegeConstant.UPDATE_USER_TYPE)
	@PutMapping("/update/{id}")
	public BaseDTO update(@PathVariable UUID id, @Valid @RequestBody MerchantTypeMasterDTO merchantTypeDTO) {
		//log.info("<== Started MerhcantTypeController.putone ==>");
		BaseDTO response = new BaseDTO();
		response = merchantTypeMasterService.update(id, merchantTypeDTO);
		//log.info("<== Ended MerhcantTypeController.putone ==>");
		return response;
	}

	@GetMapping("/get/{id}")
	public BaseDTO getById(@PathVariable UUID id) {
		//log.info("<== Started MerhcantTypeController.getone ==>");
		BaseDTO response = merchantTypeMasterService.getById(id);
		//log.info("<== Ended MerhcantTypeController.getone ==>");
		return response;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
//	@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@PostMapping("/lazylist")
	public BaseDTO getLazyList(@RequestBody PaginationRequestDTO requestData) {
		BaseDTO response = new BaseDTO();
		//log.info("<-- MerhcantTypeController.getLazyList()---> Started");
		response = merchantTypeMasterService.getLazyList(requestData);
		//log.info("<-- MerhcantTypeController.getLazyList()---> Ended");
		return response;
	}

	
	//@PreAuthorize("#oauth2.hasScope('Admin') or #oauth2.hasScope('Sales Officer') or #oauth2.hasScope('Customer Support Officer') or #oauth2.hasScope('Finance Officer') or #oauth2.hasScope('TenantAdmin') or #oauth2.hasScope('MIS')")
	@GetMapping("/getallactive")
	public BaseDTO getAllActive() {
		//log.info("<-- MerhcantTypeController.getallactive()---> Started");
		BaseDTO response = merchantTypeMasterService.getAllActive();
		//log.info("<-- MerhcantTypeController.getallactive()---> Ended");
		return response;
	}
	

	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@PutMapping("/softdelete/{id}")
	public BaseDTO softDelete(@PathVariable("id") UUID id) {
		//log.info("<--- Started MerhcantTypeController  softDelete  STARTED --->");
		BaseDTO baseDTO = merchantTypeMasterService.softDelete(id);
		//log.info("<---  MerhcantTypeController  softDelete  END --->");
		return baseDTO;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/getByAgentType/{agentType}")
	public BaseDTO getByAgentType(@PathVariable("agentType") String agentType) {
		BaseDTO baseDTO = merchantTypeMasterService.getByAgentType(agentType);
		return baseDTO;
	}
	
	
}
