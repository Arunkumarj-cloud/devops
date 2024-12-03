package com.oasys.uppcl_user__master_management.controller;

import java.util.UUID;

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
import com.oasys.uppcl_user__master_management.dto.FieldTypeMasterDTO;
import com.oasys.uppcl_user__master_management.service.FieldTypeMasterService;

import lombok.extern.log4j.Log4j2;
@RestController
@RequestMapping("/fieldType")
@Log4j2


public class FieldTypeMasterController {
	@Autowired
	FieldTypeMasterService fieldTypeMasterService;

	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@PostMapping("/create")
	public BaseDTO createFieldType(@RequestBody FieldTypeMasterDTO fieldTypeMasterDTO) {
		//log.info("<---FieldTypeMasterController.create STARTED --->");
		BaseDTO response = fieldTypeMasterService.create(fieldTypeMasterDTO);
		//log.info("<--- FieldTypeMasterController. create END --->");
		return response;
	}

	//@PreAuthorize("#oauth2.hasScope('Admin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/get/{id}")
	public BaseDTO getById(@PathVariable("id") UUID id) {
		//log.info("<--- FieldTypeMasterController getById  STARTED --->");
		BaseDTO response = fieldTypeMasterService.getById(id);
		//log.info("<--- FieldTypeMasterController getById  END --->");
		return response;
	}

	//@PreAuthorize("#oauth2.hasScope('Admin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/getall")
	public BaseDTO getAll() {
		//log.info("<--- FieldTypeMasterController getAll  STARTED --->");
		BaseDTO response = fieldTypeMasterService.getAll();
		//log.info("<--- FieldTypeMasterController getAll  END --->");
		return response;
	}

	//@PreAuthorize("#oauth2.hasScope('Admin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@PostMapping("/lazylist")
	public BaseDTO getLazyList(@RequestBody PaginationRequestDTO requestData) {
		BaseDTO response = new BaseDTO();
		//log.info("<--- FieldTypeMasterController lazylist STARTED --->");
		response = fieldTypeMasterService.getLazyList(requestData);
		//log.info("<--- FieldTypeMasterController lazylist END --->");
		return response;
	}

	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@PutMapping("/update/{id}")
	public BaseDTO update(@PathVariable UUID id, @RequestBody FieldTypeMasterDTO fieldTypeMasterDTO) {
		//log.info("<== Started FieldTypeMasterController.update() ==>");
		BaseDTO response = new BaseDTO();
		response = fieldTypeMasterService.update(id, fieldTypeMasterDTO);
		//log.info("<== Ended FieldTypeMasterController.update() ==>");
		return response;
	}

	//@PreAuthorize("#oauth2.hasScope('Admin')")
//	@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/getallactive")
	public BaseDTO getAllActive() {
		//log.info("<== Started FieldTypeMasterController.getAllActive() ==>");
		BaseDTO response = fieldTypeMasterService.getAllActive();
		//log.info("<== Ended FieldTypeMasterController.getAllActive() ==>");
		return response;
	}

	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@DeleteMapping("/softdelete/{id}")
	public BaseDTO softDelete(@PathVariable("id") UUID id) {
		//log.info("<--- Started FieldTypeMasterController  softDelete  STARTED --->");
		BaseDTO baseDTO = fieldTypeMasterService.softDelete(id);
		//log.info("<---  FieldTypeMasterController  softDelete  END --->");
		return baseDTO;
	}

}


