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
import com.oasys.uppcl_user__master_management.dto.MaritalStatusDTO;
import com.oasys.uppcl_user__master_management.service.MaritalStatusService;

import lombok.extern.log4j.Log4j2;
@RestController
@RequestMapping("/marital")
@Log4j2
public class MaritalStatusController {
	@Autowired
	MaritalStatusService maritalStatusService;
	
	
	//@PreAuthorize(PrivilegeConstant.ADD_MARITAL_STATUS)
	@PostMapping("/create")
	public BaseDTO createMaritalStatus(@Valid @RequestBody MaritalStatusDTO  maritalStatusDTO) {
		//log.info(" <====Started MaritalStatusController.createMartialStatus=====>");
		BaseDTO response=new BaseDTO();
		response = maritalStatusService.createMaritalStatus(maritalStatusDTO);
		//log.info(" <====Ended MaritalStatusController.createMartialStatus=====>");
		return response;
		
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/get")
	public BaseDTO getMaritalStatus() {
		//log.info("<===Started MaritalStatusController.getMaritalStatus ===>");
		BaseDTO response= maritalStatusService.getMaritalStatus();
		//log.info("<===Ended MaritalStatusController.getMaritalStatus ===>");
		return response;	
		}
	
	//@PreAuthorize("#oauth2.hasScope('Admin') or #oauth2.hasScope('Sales Officer') or #oauth2.hasScope('Customer Support Officer') or #oauth2.hasScope('Finance Officer')  or #oauth2.hasScope('Merchant') or #oauth2.hasScope('TenantAdmin') or"
	//		+ " #oauth2.hasScope('Third Party') or #oauth2.hasScope('districtDistributors') or #oauth2.hasScope('pincodeDistributors') or #oauth2.hasScope('SuperPincodeDistributor')  or #oauth2.hasScope('SuperDistrictDistributor') or #oauth2.hasScope('KYC Admin') or #oauth2.hasScope('Customer Care Executive')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/get/{id}")
	public BaseDTO getMaritalStatusById(@PathVariable UUID id) {
		//log.info("<===Started MaritalStatusController.getMaritalStatusById ===>");
		BaseDTO response= maritalStatusService.getMaritalStatusById(id);
		//log.info("<===Ended MaritalStatusController.getMaritalStatusById ===>");
		return response;	
		}
	
	//@PreAuthorize(PrivilegeConstant.UPDATE_MARITAL_STATUS)
	@PutMapping("/update/{id}")
	public BaseDTO updateMaritalStatus(@PathVariable UUID id,@Valid @RequestBody MaritalStatusDTO maritalStatusDTO) {
		//log.info("<===Started MaritalStatusController.updateMaritalStatus ===>");
		BaseDTO response= maritalStatusService.updateMaritalStatus(id,maritalStatusDTO);
		//log.info("<===Ended MaritalStatusController.updateMaritalStatus ===>");
		return response;	
		}

	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@PutMapping("/softdelete/{id}")
	public BaseDTO sofeDelete(@PathVariable UUID id) {
		//log.info("<===Started MaritalStatusController.sofeDelete ===>");
		BaseDTO response= maritalStatusService.softDelete(id);
		//log.info("<===Ended MaritalStatusController.sofeDelete ===>");
		return response;	
		}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@PostMapping("/lazylist")
	public BaseDTO getAllMaritalStatuslazy(@RequestBody PaginationRequestDTO pageData) {
		//log.info("<== Started MaritalStatusController.getAllMaritalStatuslazy() ==>");
		BaseDTO response = maritalStatusService.getAllMaritalStatuslazy(pageData);
		//log.info("<== Ended MaritalStatusController.getAllMaritalStatuslazy()  ==>");
		return response;
	}

	//@PreAuthorize("#oauth2.hasScope('Admin') or #oauth2.hasScope('Sales Officer') or #oauth2.hasScope('Customer Support Officer') or #oauth2.hasScope('Finance Officer') or"
	//		+ " #oauth2.hasScope('TenantAdmin') or #oauth2.hasScope('Third Party') or #oauth2.hasScope('districtDistributors') or #oauth2.hasScope('pincodeDistributors') or #oauth2.hasScope('SuperPincodeDistributor')  or #oauth2.hasScope('SuperDistrictDistributor') or #oauth2.hasScope('KYC Admin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/getallactive")
	public BaseDTO getAllActive() {
		//log.info("<== Started MaritalStatusController.getAllActive() ==>");
		BaseDTO response = maritalStatusService.getAllActive();
		//log.info("<== Ended MaritalStatusController.getAllActive()  ==>");
		return response;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@DeleteMapping("/delete/{id}")
	public BaseDTO deleteMarital(@PathVariable("id") UUID id) {
		//log.info("<--- MaritalStatusController delete  STARTED --->");
		BaseDTO response = maritalStatusService.deleteMarital(id);
		//log.info("<--- MaritalStatusController delete  END --->");
		return response;
	}


}
