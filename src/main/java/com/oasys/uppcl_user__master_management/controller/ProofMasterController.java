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
import com.oasys.uppcl_user__master_management.dto.ProofMasterDTO;
import com.oasys.uppcl_user__master_management.service.ProofMasterService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/proof")
@Log4j2
public class ProofMasterController {

	@Autowired
	ProofMasterService proofService; 

	@GetMapping("/getAll")
	public BaseDTO getAll() {
		//log.info("<== Started ProofController.getAll() ==>");
		BaseDTO response = new BaseDTO();
		response = proofService.getAll();
		//log.info("<== Ended ProofController.getAll() ==>");
		return response;
	}

	
	@PostMapping("/create")
	public BaseDTO create(@Valid @RequestBody ProofMasterDTO proofDTO) {
		//log.info("<== Started ProofController.create ==>");
		BaseDTO response = new BaseDTO();
		response = proofService.create(proofDTO);
		//log.info("<== Ended ProofController.create ==>");
		return response;
	}

	@DeleteMapping("/delete/{id}")
	public BaseDTO delete(@PathVariable UUID id) {
		//log.info("<== Started ProofController.delete ==>");
		BaseDTO response = new BaseDTO();
		response = proofService.delete(id);
		//log.info("<== Ended ProofController.delete ==>");
		return response;
	}

	
	@PutMapping("/update/{id}")
	public BaseDTO update(@PathVariable UUID id, @Valid @RequestBody ProofMasterDTO proofDTO) {
		//log.info("<== Started ProofController.update ==>");
		BaseDTO response = new BaseDTO();
		response = proofService.update(id, proofDTO);
		//log.info("<== Ended ProofController.update ==>");
		return response;
	}

	//@PreAuthorize("isAuthenticated()")
	@GetMapping("/get/{id}")
	public BaseDTO getById(@PathVariable UUID id) {
		//log.info("<== Started ProofController.getById ==>");
		BaseDTO response = proofService.getById(id);
		//log.info("<== Ended ProofController.getById ==>");
		return response;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	
	@PostMapping("/lazylist")
	public BaseDTO getLazyList(@RequestBody PaginationRequestDTO requestData) {
		BaseDTO response = new BaseDTO();
		//log.info("<== Started ProofController.getLazyList ==>");
		response = proofService.getLazyList(requestData);
		//log.info("<== Ended ProofController.getLazyList ==>");
		return response;
	}

	//@PreAuthorize("#oauth2.hasScope('Admin') or #oauth2.hasScope('Merchant') or #oauth2.hasScope('Third Party') or #oauth2.hasScope('Sales Officer') or #oauth2.hasScope('Customer Support Officer') or "
	//		+ "#oauth2.hasScope('Finance Officer') or #oauth2.hasScope('TenantAdmin') or #oauth2.hasScope('districtDistributors') or #oauth2.hasScope('pincodeDistributors') or #oauth2.hasScope('SuperPincodeDistributor')  or #oauth2.hasScope('SuperDistrictDistributor') or #oauth2.hasScope('KYC Admin')")
	
	@GetMapping("/getallactive")
	public BaseDTO getAllActive() {
		//log.info("<== Started ProofController.getAllActive ==>");
		BaseDTO response = proofService.getAllActive();
		//log.info("<== Ended ProofController.getAllActive ==>");
		return response;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin') or #oauth2.hasScope('Merchant') or #oauth2.hasScope('Third Party') or #oauth2.hasScope('Sales Officer') or #oauth2.hasScope('Customer Support Officer') or "
	//		+ "#oauth2.hasScope('Finance Officer') or #oauth2.hasScope('TenantAdmin') or #oauth2.hasScope('districtDistributors') or #oauth2.hasScope('pincodeDistributors') or #oauth2.hasScope('SuperPincodeDistributor')  or #oauth2.hasScope('SuperDistrictDistributor') or #oauth2.hasScope('KYC Admin')")
	
	@GetMapping("/getbyprooftype/{id}")
	public BaseDTO getByProofType(@PathVariable UUID id) {
		//log.info("<== Started ProofController.getByProofType ==>");
		BaseDTO response = proofService.getByProofType(id);
		//log.info("<== Ended ProofController.getByProofType ==>");
		return response;
	}
	
	
	@PutMapping("/softdelete/{id}")
	public BaseDTO softDelete(@PathVariable("id") UUID id) {
		//log.info("<---  ProofController softdelete STARTED --->");
		BaseDTO response = proofService.softDelete(id);
		//log.info("<--- ProofController softdelete  END --->");
		return response;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin') or #oauth2.hasScope('Merchant') ")
	
	@GetMapping("/getproofnamebyid/{id}")
	public BaseDTO getProofNameById(@PathVariable UUID id) {
		//log.info("<== Started ProofController.getProofNameById ==>");
		BaseDTO response = proofService.getProofNameById(id);
		//log.info("<== Ended ProofController.getProofNameById ==>");
		return response;
	}
}

