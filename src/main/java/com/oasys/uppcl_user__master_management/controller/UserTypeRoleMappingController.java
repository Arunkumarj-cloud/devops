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
import com.oasys.uppcl_user__master_management.dto.UserTypeRoleMappingDTO;
import com.oasys.uppcl_user__master_management.service.UserTypeRoleMappingService;

import lombok.extern.log4j.Log4j2;
@RestController
@RequestMapping("/usertyperole")
@Log4j2
public class UserTypeRoleMappingController {
	
	@Autowired
	UserTypeRoleMappingService userTypeRoleMappingService;

	
	@PostMapping("/create")
	public BaseDTO create(@Valid @RequestBody UserTypeRoleMappingDTO userTypeRoleMappingDTO) {
		//log.info("<--- UserTypeRoleMapping create Controller STARTED --->");
		BaseDTO response = userTypeRoleMappingService.create(userTypeRoleMappingDTO);
		//log.info("<--- UserTypeRoleMapping create Controller END --->");
		return response;
	}


	
	@PutMapping("/update/{id}")
	public BaseDTO update(@PathVariable("id") UUID id,@Valid @RequestBody UserTypeRoleMappingDTO userTypeRoleMappingDTO) {
		//log.info("<--- UserTypeRoleMapping update Controller STARTED --->");
		BaseDTO response = userTypeRoleMappingService.update(id,userTypeRoleMappingDTO);
		//log.info("<--- UserTypeRoleMapping update Controller END --->");
		return response;
	}

	
	@GetMapping("/get/{id}")
	public BaseDTO getById(@PathVariable("id") UUID id) {
		//log.info("<--- UserTypeRoleMapping getById Controller STARTED --->");
		BaseDTO response = userTypeRoleMappingService.getById(id);
		//log.info("<--- UserTypeRoleMapping getById Controller END --->");
		return response;
	}
	
	
	@GetMapping("/getall")
	public BaseDTO getAll() {
		//log.info("<--- UserTypeRoleMapping getAll Controller STARTED --->");
		BaseDTO response = userTypeRoleMappingService.getAll();
		//log.info("<--- UserTypeRoleMapping getAll Controller END --->");
		return response;
	}
	
	@GetMapping("/getallactive")
	public BaseDTO getAllActive() {
		//log.info("<--- UserTypeRoleMapping getAll Controller STARTED --->");
		BaseDTO response = userTypeRoleMappingService.getAllActive();
		//log.info("<--- UserTypeRoleMapping getAll Controller END --->");
		return response;
	}
	
	
	@PostMapping("/lazylist")
	public BaseDTO getLazyList(@RequestBody PaginationRequestDTO requestData) {
		//log.info("<--- UserTypeRoleMapping lazylist Controller STARTED --->");
		BaseDTO response = userTypeRoleMappingService.getLazyList(requestData);
		//log.info("<--- UserTypeRoleMapping lazylist Controller END --->");
		return response;
	}

	
	@DeleteMapping("/delete/{id}")
	public BaseDTO delete(@PathVariable("id") UUID id) {
		//log.info("<--- UserTypeRoleMapping delete Controller STARTED --->");
		BaseDTO response = userTypeRoleMappingService.delete(id);
		//log.info("<--- UserTypeRoleMapping delete Controller END --->");
		return response;
	}
	
	
	@PutMapping("/softdelete/{id}")
	public BaseDTO softDelete(@PathVariable("id") UUID id) {
		//log.info("<--- UserTypeRoleMapping softDelete Controller STARTED --->");
		BaseDTO response = userTypeRoleMappingService.softDelete(id);
		//log.info("<--- UserTypeRoleMapping softDelete Controller END --->");
		return response;
	}
	
	
	@GetMapping("/getbyusertype/{id}")
	public BaseDTO getByUserTypeId(@PathVariable("id") UUID id) {
		//log.info("<--- UserTypeRoleMapping getByUserTypeId Controller STARTED --->");
		BaseDTO response = userTypeRoleMappingService.getByUserTypeId(id);
		//log.info("<--- UserTypeRoleMapping getByUserTypeId Controller END --->");
		return response;
	}

}
