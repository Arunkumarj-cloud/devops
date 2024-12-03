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
import com.oasys.uppcl_user__master_management.dto.EducationDTO;
import com.oasys.uppcl_user__master_management.service.DataTypeService;
import com.oasys.uppcl_user__master_management.service.EducationService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/education")
@AllArgsConstructor
@Log4j2
public class EducationController {
	@Autowired
	EducationService educationService;
	
	//@PreAuthorize(PrivilegeConstant.ADD_EDUCATION)
	@PostMapping("/create")
	public BaseDTO create(@Valid @RequestBody EducationDTO educationDTO) {
		//log.info("<--- EducationController create  STARTED --->");
		BaseDTO response = educationService.create(educationDTO);
		//log.info("<--- EducationController create  END --->");
		return response;
	}
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/getallactive")
	public BaseDTO getAllActive() {
		//log.info("<--- EducationController getAll  STARTED --->");
		BaseDTO response = educationService.getAllActive();
		//log.info("<--- EducationController getAll  END --->");
		return response;
	}
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@PostMapping("/lazylist")
	public BaseDTO getLazyList(@RequestBody PaginationRequestDTO requestData) {
	BaseDTO response = new BaseDTO();
	//log.info("<--- EducationController lazylist STARTED --->");
	response = educationService.getLazyList(requestData);
	//log.info("<--- EducationController lazylist END --->");
	return response;
	}
	//@PreAuthorize(PrivilegeConstant.UPDATE_EDUCATION)
	@PutMapping("/update/{id}")
	public BaseDTO update(@PathVariable UUID id, @Valid @RequestBody EducationDTO educationDTO) {
		//log.info("<== Started EducationController.update() ==>");
		BaseDTO response = new BaseDTO();
		response = educationService.update(id, educationDTO);
		//log.info("<== Ended EducationController.update() ==>");
		return response;
	}
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/get/{id}")
	public BaseDTO getById(@PathVariable("id") UUID id) {
		//log.info("<--- EducationController getById  STARTED --->");
		BaseDTO response = educationService.getById(id);
		//log.info("<--- EducationController getById  END --->");
		return response;
	}
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@DeleteMapping("/softdelete/{id}")
	public BaseDTO softDelete(@PathVariable("id") UUID id) {
		//log.info("<--- Started EducationController  softDelete  STARTED --->");
		BaseDTO baseDTO = educationService.softDelete(id);
		//log.info("<---  EducationController  softDelete  END --->");
		return baseDTO;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/getall")
	public BaseDTO getAll() {
		//log.info("<--- EducationController getAll  STARTED --->");
		BaseDTO response = educationService.getAll();
		//log.info("<--- EducationController getAll  END --->");
		return response;
	}
	

}
