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
import com.oasys.uppcl_user__master_management.dto.BankBranchDTO;
import com.oasys.uppcl_user__master_management.service.BankBranchService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/bankbranch")
@Log4j2
public class BankBranchController {
	@Autowired
	BankBranchService bankBranchService;

	//@PreAuthorize(PrivilegeConstant.ADD_BANK_BRANCH)
	@PostMapping("/create")
	public BaseDTO createBankBranch(@Valid @RequestBody BankBranchDTO bankBranchDTO) {
		//log.info("<--- BankBranch create Controller STARTED --->");
		BaseDTO response = bankBranchService.create(bankBranchDTO);
		//log.info("<--- BankBranch create Controller END --->");
		return response;
	}
	
	
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/getall")
	public BaseDTO getAll() {
		//log.info("<--- BankBranch getAll Controller STARTED --->");
		BaseDTO response = bankBranchService.getAll();
		///log.info("<--- BankBranch getAll Controller END --->");
		return response;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/getallactive")
	public BaseDTO getAllActive() {
		//log.info("<--- BankBranch getAll Controller STARTED --->");
		BaseDTO response = bankBranchService.getAllActive();
		//log.info("<--- BankBranch getAll Controller END --->");
		return response;
	}

	//@PreAuthorize(PrivilegeConstant.UPDATE_BANK_BRANCH)
	@PutMapping("/update/{id}")
	public BaseDTO updateBankBranch(@PathVariable("id") UUID id,@Valid @RequestBody BankBranchDTO bankBranchDTO) {
		//log.info("<--- BankBranch update Controller STARTED --->");
		BaseDTO response = bankBranchService.update(id,bankBranchDTO);
		//log.info("<--- BankBranch update Controller END --->");
		return response;
	}
		
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@PostMapping("/lazylist")
	public BaseDTO getLazyList(@RequestBody PaginationRequestDTO requestData) {
	BaseDTO response = new BaseDTO();
	//log.info("<--- BankBranch lazylist Controller STARTED --->");
	response = bankBranchService.getLazyList(requestData);
	//log.info("<--- BankBranch lazylist Controller END --->");
	return response;
	}

	//@PreAuthorize("#oauth2.hasScope('Admin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/get/{id}")
	public BaseDTO getById(@PathVariable("id") UUID id) {
		//log.info("<--- BankBranch getById Controller STARTED --->");
		BaseDTO response = bankBranchService.getById(id);
		//log.info("<--- BankBranch getById Controller END --->");
		return response;
	}

	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@DeleteMapping("/delete/{id}")
	public BaseDTO deleteBankBranch(@PathVariable("id") UUID id) {
		//log.info("<--- BankBranch delete Controller STARTED --->");
		BaseDTO response = bankBranchService.delete(id);
		//log.info("<--- BankBranch delete Controller END --->");
		return response;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@PutMapping("/softdelete/{id}")
	public BaseDTO softDelete(@PathVariable("id") UUID id) {
		//log.info("<--- BankBranch softDelete Controller STARTED --->");
		BaseDTO response = bankBranchService.softDelete(id);
		//log.info("<--- BankBranch softDelete Controller END --->");
		return response;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin') or #oauth2.hasScope('Sales Officer') or #oauth2.hasScope('Merchant') or #oauth2.hasScope('Third Party')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/getByBankNameId/{id}")
	public BaseDTO getByBankNameId(@PathVariable("id") UUID id) {
		//log.info("<--- BankBranch getByBankNameId Controller STARTED --->");
		BaseDTO response = bankBranchService.getByBankNameId(id);
		//log.info("<--- BankBranch getByBankNameId Controller END --->");
		return response;
	}
	
	

}
