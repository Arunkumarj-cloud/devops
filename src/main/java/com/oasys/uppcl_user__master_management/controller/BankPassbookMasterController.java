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
import com.oasys.uppcl_user__master_management.dto.BankPassbookMasterDTO;
import com.oasys.uppcl_user__master_management.service.BankPassbookMasterService;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping("/passbook")
public class BankPassbookMasterController {
	
	@Autowired
	BankPassbookMasterService bankPassbookMasterService;

	@GetMapping("/getAll")
	public BaseDTO getAll() {
		//log.info("<== Started BankPassbookMasterController.getAll() ==>");
		BaseDTO response = new BaseDTO();
		response = bankPassbookMasterService.getAll();
		//log.info("<== Ended BankPassbookMasterController.getAll() ==>");
		return response;
	}
	
	//@PreAuthorize(PrivilegeConstant.ADD_BANK_PROOF)
	@PostMapping("/create")
	public BaseDTO create(@Valid @RequestBody BankPassbookMasterDTO bankPassbookMasterDTO) {
		//log.info("<== Started BankPassbookMasterController.create() ==>");
		BaseDTO response = new BaseDTO();
		response = bankPassbookMasterService.create(bankPassbookMasterDTO);
		//log.info("<== Ended BankPassbookMasterController.create() ==>");
		return response;
	}
	
	//@PreAuthorize(PrivilegeConstant.UPDATE_BANK_PROOF)
	@PutMapping("/update/{id}")
	public BaseDTO update(@PathVariable UUID id, @Valid @RequestBody BankPassbookMasterDTO bankPassbookMasterDTO) {
		//log.info("<== Started BankPassbookMasterController.update() ==>");
		BaseDTO response = new BaseDTO();
		response = bankPassbookMasterService.update(id, bankPassbookMasterDTO);
		//log.info("<== Ended BankPassbookMasterController.update() ==>");
		return response;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/get/{id}")
	public BaseDTO getById(@PathVariable UUID id) {
		//log.info("<== Started BankPassbookMasterController.getById() ==>");
		BaseDTO response = new BaseDTO();
		response = bankPassbookMasterService.getById(id);
		//log.info("<== Ended BankPassbookMasterController.getById() ==>");
		return response;
	}
	
	@DeleteMapping("/delete/{id}")
	public BaseDTO delete(@PathVariable UUID id) {
		//log.info("<== Started BankPassbookMasterController.delete() ==>");
		BaseDTO response = new BaseDTO();
		response = bankPassbookMasterService.delete(id);
		//log.info("<== Ended BankPassbookMasterController.delete() ==>");
		return response;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@PostMapping("/lazylist")
	public BaseDTO getLazyList(@RequestBody PaginationRequestDTO requestData) {
		BaseDTO response = new BaseDTO();
		//log.info("<== Started BankPassbookMasterController.getLazyList() ==>");
		response = bankPassbookMasterService.getAllLazy(requestData);
		//log.info("<== Ended BankPassbookMasterController.getLazyList() ==>");
		return response;
	}

	//@PreAuthorize("#oauth2.hasScope('Admin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/getallactive")
	public BaseDTO getAllActive() {
		//log.info("<== Started BankPassbookMasterController.getAllActive() ==>");
		BaseDTO response = bankPassbookMasterService.getAllActive();
		//log.info("<== Ended BankPassbookMasterController.getAllActive() ==>");
		return response;
	}

	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@DeleteMapping("/softdelete/{id}")
	public BaseDTO softDelete(@PathVariable("id") UUID id) {
		//log.info("<--- Started BankPassbookMasterController  softDelete  STARTED --->");
		BaseDTO baseDTO = bankPassbookMasterService.softDelete(id);
		//log.info("<---  BankPassbookMasterController  softDelete  END --->");
		return baseDTO;
	}

}
