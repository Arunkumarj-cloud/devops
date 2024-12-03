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
import com.oasys.uppcl_user__master_management.dto.BankTypeMasterDTO;
import com.oasys.uppcl_user__master_management.service.BankTypeMasterService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/banktype")

public class BankTypeMasterController {
	
	@Autowired
	BankTypeMasterService bankTypeService;

	@GetMapping("/getall")
	public BaseDTO getAll() {
		//log.info("<== Started BankTypeMasterController.getAll() ==>");
		BaseDTO response = new BaseDTO();
		response = bankTypeService.getAll();
		//log.info("<== Ended BankTypeMasterController.getAll() ==>");
		return response;
	}

	//@PreAuthorize(PrivilegeConstant.ADD_BANK_TYPE)
	@PostMapping("/create")
	public BaseDTO create(@Valid @RequestBody BankTypeMasterDTO bankTypeDto) {
		//log.info("<== Started BankTypeMasterController.create() ==>");
		BaseDTO response = new BaseDTO();
		response = bankTypeService.create(bankTypeDto);
		//log.info("<== Ended BankTypeMasterController.create() ==>");
		return response;
	}

	@DeleteMapping("/delete/{id}")
	public BaseDTO delete(@PathVariable UUID id) {
		//log.info("<== Started BankTypeMasterController.delete() ==>");
		BaseDTO response = new BaseDTO();
		response = bankTypeService.delete(id);
		//log.info("<== Ended BankTypeMasterController.delete() ==>");
		return response;
	}

//	@PreAuthorize(PrivilegeConstant.UPDATE_BANK_TYPE)
	@PutMapping("/update/{id}")
	public BaseDTO update(@PathVariable UUID id, @Valid @RequestBody BankTypeMasterDTO bankTypeDto) {
		//log.info("<== Started BankTypeMasterController.update() ==>");
		BaseDTO response = new BaseDTO();
		response = bankTypeService.update(id, bankTypeDto);
		//log.info("<== Ended BankTypeMasterController.update() ==>");
		return response;
	}

	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/get/{id}")
	public BaseDTO getById(@PathVariable UUID id) {
		//log.info("<== Started BankTypeMasterController.getById() ==>");
		BaseDTO response = bankTypeService.getById(id);
		//log.info("<== Ended BankTypeMasterController.getById()==>");
		return response;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@PostMapping("/lazylist")
	public BaseDTO getLazyList(@RequestBody PaginationRequestDTO requestData) {
		BaseDTO response = new BaseDTO();
		//log.info("<-- BankTypeMasterController.getLazyList()---> Started");
		response = bankTypeService.getAllLazy(requestData);
		//log.info("<-- BankTypeMasterController.getLazyList()---> Ended");
		return response;
	}

	//@PreAuthorize("#oauth2.hasScope('Admin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/getallactive")
	public BaseDTO getAllActive() {
		//log.info("<-- BankTypeMasterController.getallactive()---> Started");
		BaseDTO response = bankTypeService.getAllActive();
		//log.info("<-- BankTypeMasterController.getallactive()---> Ended");
		return response;
	}
	
	@PutMapping("/softdelete/{id}")
	public BaseDTO softDelete(@PathVariable("id") UUID id) {
		//log.info("<--- Started BankTypeMasterController  softDelete  STARTED --->");
		BaseDTO baseDTO = bankTypeService.softDelete(id);
		//log.info("<---  BankTypeMasterController  softDelete  END --->");
		return baseDTO;
	}

}
