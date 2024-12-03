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
import com.oasys.uppcl_user__master_management.dto.BankNameDTO;
import com.oasys.uppcl_user__master_management.service.BankNameService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/bankname")
@Log4j2
public class BankNameController {

	@Autowired
	BankNameService bankNameService;
     
	//@PreAuthorize(PrivilegeConstant.ADD_BANK)
	@PostMapping("/create")
	public BaseDTO createBankName(@Valid @RequestBody BankNameDTO bankNameDTO) {
		//log.info("<--- BankName create Controller STARTED --->");
		BaseDTO response = bankNameService.create(bankNameDTO);
		//log.info("<--- BankName create Controller END --->");
		return response;
	}

	//@PreAuthorize("#oauth2.hasScope('Admin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/getall")
	public BaseDTO getAll() {
		//log.info("<--- BankName getAll Controller STARTED --->");
		BaseDTO response = bankNameService.getAll();
		//log.info("<--- BankName getAll Controller END --->");
		return response;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin') or #oauth2.hasScope('Sales Officer') or #oauth2.hasScope('Customer Support Officer') or #oauth2.hasScope('Finance Officer') "
	//		+ " or #oauth2.hasScope('Merchant') or #oauth2.hasScope('TenantAdmin') or #oauth2.hasScope('Third Party') or #oauth2.hasScope('districtDistributors') or #oauth2.hasScope('pincodeDistributors')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/getallactive")
	public BaseDTO getAllActive() {
		//log.info("<--- BankName getAll Controller STARTED --->");
		BaseDTO response = bankNameService.getAllActive();
		//log.info("<--- BankName getAll Controller END --->");
		return response;
	}
    
	//@PreAuthorize(PrivilegeConstant.UPDATE_BANK)
	@PutMapping("/update/{id}")
	public BaseDTO updateBankName(@PathVariable("id") UUID id,@Valid @RequestBody BankNameDTO bankNameDTO) {
		//log.info("<--- BankName update Controller STARTED --->");
		BaseDTO response = bankNameService.update(id,bankNameDTO);
		//log.info("<--- BankName update Controller END --->");
		return response;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@PostMapping("/lazylist")
	public BaseDTO getLazyList(@RequestBody PaginationRequestDTO requestData) {
	BaseDTO response = new BaseDTO();
	//log.info("<--- BankName lazylist Controller STARTED --->");
	response = bankNameService.getLazyList(requestData);
	//log.info("<--- BankName lazylist Controller END --->");
	return response;
	}

	//@PreAuthorize("#oauth2.hasScope('Admin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/get/{id}")
	public BaseDTO getById(@PathVariable("id") UUID id) {
		//log.info("<--- BankName getById Controller STARTED --->");
		BaseDTO response = bankNameService.getById(id);
		//log.info("<--- BankName getById Controller END --->");
		return response;
	}

	@DeleteMapping("/delete/{id}")
	public BaseDTO delete(@PathVariable("id") UUID id) {
		//log.info("<--- BankName delete Controller STARTED --->");
		BaseDTO response = bankNameService.delete(id);
		//log.info("<--- BankName delete Controller END --->");
		return response;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@PutMapping("/softdelete/{id}")
	public BaseDTO softDelete(@PathVariable("id") UUID id) {
		//log.info("<--- BankName delete Controller STARTED --->");
		BaseDTO response = bankNameService.softDelete(id);
		//log.info("<--- BankName delete Controller END --->");
		return response;
	}
	

	/*@PreAuthorize("#oauth2.hasScope('Admin')")
	@GetMapping("/getByBanktype/{id}")
	public BaseDTO getByBanktypeId(@PathVariable("id") UUID id) {
		log.info("<--- BankName getByBanktypeId Controller STARTED --->");
		BaseDTO response = bankNameService.getByBanktypeId(id);
		log.info("<--- BankName getByBanktypeId Controller END --->");
		return response;
	}
	*/
	/*
	 * @PostMapping("/lazylist2") public BaseDTO userSearchFilter(@RequestBody
	 * BankNameDTO requestData) { BaseDTO response = new BaseDTO();
	 * log.info("<--- BankName lazylist Controller STARTED --->"); response =
	 * bankNameService.userSearchFilter(requestData);
	 * log.info("<--- BankName lazylist Controller END --->"); return response; }
	 */

}
