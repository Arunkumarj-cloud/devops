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
import com.oasys.uppcl_user__master_management.dto.FieldMasterDTO;
import com.oasys.uppcl_user__master_management.service.FieldMasterService;

import lombok.extern.log4j.Log4j2;
@RestController
@RequestMapping("/field")
@Log4j2
public class FieldMasterController {

	@Autowired(required = false)
	FieldMasterService fieldMasterService;

	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@PostMapping("/create")
	public BaseDTO create(@RequestBody FieldMasterDTO fieldMasterDTO) {
		//log.info("<---FieldMasterController.create() STARTED --->");
		BaseDTO response = fieldMasterService.create(fieldMasterDTO);
		//log.info("<--- FieldMasterController. create() END --->");
		return response;
	}

	//@PreAuthorize("#oauth2.hasScope('Admin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/get/{id}")
	public BaseDTO getById(@PathVariable("id") UUID id) {
		//log.info("<--- FieldMasterController getById()  STARTED --->");
		BaseDTO response = fieldMasterService.getById(id);
		//log.info("<--- FieldMasterController getById()  END --->");
		return response;
	}

	//@PreAuthorize("#oauth2.hasScope('Admin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/getall")
	public BaseDTO getAll() {
		//log.info("<--- FieldMasterController getAll  STARTED --->");
		BaseDTO response = fieldMasterService.getAll();
		//log.info("<--- FieldMasterController getAll  END --->");
		return response;
	}

	//@PreAuthorize("#oauth2.hasScope('Admin')")
//	@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/getallactive")
	public BaseDTO getAllActive() {
		//log.info("<== Started FieldMasterController.getAllActive() ==>");
		BaseDTO response = fieldMasterService.getAllActive();
		//log.info("<== Ended FieldMasterController.getAllActive() ==>");
		return response;
	}

//	@PreAuthorize("#oauth2.hasScope('Admin')")
	@DeleteMapping("/softdelete/{id}")
	public BaseDTO softDelete(@PathVariable("id") UUID id) {
		//log.info("<--- Started FieldMasterController  softDelete  STARTED --->");
		BaseDTO response = fieldMasterService.softDelete(id);
		//log.info("<---  FieldMasterController  softDelete  END --->");
		return response;
	}

	//@PreAuthorize("#oauth2.hasScope('Admin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@PostMapping("/lazylist")
	public BaseDTO lazyList(@Valid @RequestBody PaginationRequestDTO pageData) {
		//log.info("<----------Started FieldMasterController.lazyList()-----> ");
		BaseDTO response = new BaseDTO();
		response = fieldMasterService.lazyList(pageData);
		//log.info("<----------Ended FieldMasterController.lazyList()-----> ");
		return response;
	}

//	@PreAuthorize("#oauth2.hasScope('Admin')")
	@PutMapping("/update/{id}")
	public BaseDTO update(@PathVariable("id") UUID id, @RequestBody FieldMasterDTO fieldMasterDTO) {
		//log.info("<----------Started FieldMasterController.updateTemplate()-----> ");
		BaseDTO response = fieldMasterService.update(id, fieldMasterDTO);
		//log.info(" <----------Ended FieldMasterController.updateTemplate()----->  ");
		return response;
	}

}

