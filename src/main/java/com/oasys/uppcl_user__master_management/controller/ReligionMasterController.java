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
import com.oasys.uppcl_user__master_management.dto.ReligionMasterDTO;
import com.oasys.uppcl_user__master_management.service.ReligionMasterService;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping("/religion")
public class ReligionMasterController {
	
	@Autowired
	ReligionMasterService religionMasterService;
	
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/getAll")
	public BaseDTO getAll() {
		//log.info("<== Started ReligionMasterController.getAll() ==>");
		BaseDTO response = new BaseDTO();
		response = religionMasterService.getAll();
		//log.info("<== Ended ReligionMasterController.getAll() ==>");
		return response;
	}
	
	//@PreAuthorize(PrivilegeConstant.ADD_RELIGION)
	@PostMapping("/create")
	public BaseDTO create(@Valid @RequestBody ReligionMasterDTO religionMasterDTO) {
		//log.info("<== Started ReligionMasterController.create() ==>");
		BaseDTO response = new BaseDTO();
		response = religionMasterService.create(religionMasterDTO);
		//log.info("<== Ended ReligionMasterController.create() ==>");
		return response;
	}
	
	//@PreAuthorize(PrivilegeConstant.UPDATE_RELIGION)
	@PutMapping("/update/{id}")
	public BaseDTO update(@PathVariable UUID id,@Valid @RequestBody ReligionMasterDTO religionMasterDTO) {
		//log.info("<== Started ReligionMasterController.update() ==>");
		BaseDTO response = new BaseDTO();
		response = religionMasterService.update(id, religionMasterDTO);
		//log.info("<== Ended ReligionMasterController.update() ==>");
		return response;
	}
	
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/get/{id}")
	public BaseDTO getById(@PathVariable UUID id) {
		//log.info("<== Started ReligionMasterController.getById() ==>");
		BaseDTO response = new BaseDTO();
		response = religionMasterService.getById(id);
		//log.info("<== Ended ReligionMasterController.getById() ==>");
		return response;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@DeleteMapping("/delete/{id}")
	public BaseDTO delete(@PathVariable UUID id) {
		//log.info("<== Started ReligionMasterController.delete() ==>");
		BaseDTO response = new BaseDTO();
		response = religionMasterService.delete(id);
		//log.info("<== Ended ReligionMasterController.delete() ==>");
		return response;
	}
	
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@PostMapping("/lazylist")
	public BaseDTO getLazyList(@RequestBody PaginationRequestDTO requestData) {
		BaseDTO response = new BaseDTO();
		//log.info("<== Started  ReligionMasterController.getLazyList()==> ");
		response = religionMasterService.getAllLazy(requestData);
		//log.info("<== Ended ReligionMasterController.getLazyList() ==> ");
		return response;
	}

	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/getallactive")
	public BaseDTO getAllActive() {
		//log.info("<== Started ReligionMasterController.getallactive() ==> ");
		BaseDTO response = religionMasterService.getAllActive();
		//log.info("<== Ended ReligionMasterController.getallactive() ==>");
		return response;
	}	
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@DeleteMapping("/softdelete/{id}")
	public BaseDTO softDelete(@PathVariable("id") UUID id) {
		//log.info("<== Started ReligionMasterController.getallactive() ==>");
		BaseDTO response = religionMasterService.softDelete(id);
		//log.info("<== Ended ReligionMasterController.getallactive() ==>");
		return response;
	}
}
