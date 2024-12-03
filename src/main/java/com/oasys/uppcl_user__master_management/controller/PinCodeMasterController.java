package com.oasys.uppcl_user__master_management.controller;

import java.util.Set;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.PinCodeMasterDTO;
import com.oasys.uppcl_user__master_management.service.PinCodeMasterService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/pinCode")
@Log4j2
public class PinCodeMasterController {

	@Autowired
	PinCodeMasterService pinCodeService;
	
	
//	@PreAuthorize("#oauth2.hasScope('Admin')")
    @PostMapping("/create")
	public BaseDTO create(@Valid @RequestBody PinCodeMasterDTO pinCodeMasterDTO) {
		return  pinCodeService.create(pinCodeMasterDTO);
		
	}
	
//	@PreAuthorize("#oauth2.hasScope('Admin') ")
	@PostMapping("/uploadPincodeexcelfile")
	public BaseDTO uploadPincodeexcelfile(@RequestParam("file") MultipartFile file) {
		BaseDTO response = new BaseDTO();
		response = pinCodeService.uploadExcelFile(file);
		return response;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@PostMapping("/lazylist")
	public BaseDTO getLazyList(@RequestBody PaginationRequestDTO pageData) {
	BaseDTO response = new BaseDTO();
	response = pinCodeService.getLazyList(pageData);
	return response;
	}
    
//	@PreAuthorize("isAuthenticated()")
	@GetMapping("/getAll")
	public BaseDTO getAll() {
		return  pinCodeService.getAll();
		
	}

	
//	@PreAuthorize("isAuthenticated()")
	@GetMapping("/getallactive")
	public BaseDTO getAllActive() {
		return  pinCodeService.getAllActive();
		
	}

//	@PreAuthorize("#oauth2.hasScope('Admin')")
	@PutMapping("/update/{id}")
	public BaseDTO update(@PathVariable("id") UUID id, @Valid @RequestBody PinCodeMasterDTO pinCodeMasterDTO) {
		return  pinCodeService.update(id,pinCodeMasterDTO);

	}
    
//	@PreAuthorize("isAuthenticated()")
	@GetMapping("/get/{id}")
	public BaseDTO getById(@PathVariable("id") UUID id) {
		
		return pinCodeService.getById(id);
		
	}
	
//	@PreAuthorize("isAuthenticated()")
	@GetMapping("/getby/{ids}")
	public BaseDTO getByIds(@PathVariable("ids") Set<UUID> ids) {
		return pinCodeService.getByIds(ids);
		
	}
     
	
//	@PreAuthorize("#oauth2.hasScope('Admin')")
	@DeleteMapping("/delete/{id}")
	public BaseDTO delete(@PathVariable("id") UUID id) {
		return pinCodeService.delete(id);
		
	}
	
	
//	@PreAuthorize("#oauth2.hasScope('Admin')")
	@PutMapping("/softdelete/{id}")
	public BaseDTO softDelete(@PathVariable("id") UUID id) {
		return pinCodeService.softDelete(id);
	}
	
	
	//@PreAuthorize("isAuthenticated()")
	@GetMapping("/getByDistrictId/{id}")
	public BaseDTO getByDistrictId(@PathVariable("id") UUID id) {
		
		return pinCodeService.getByDistrictId(id);
		
	}
	
	//@PreAuthorize("isAuthenticated()")
	@GetMapping("/getByPinCode/{code}")
	public BaseDTO getByDistrictId(@PathVariable("code") String pinCode) {
		
		return pinCodeService.findByPinCode(pinCode);
		
	}

}
