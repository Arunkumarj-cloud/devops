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
import com.oasys.uppcl_user__master_management.dto.DataTypeDTO;
import com.oasys.uppcl_user__master_management.service.DataTypeService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/datatype")
@AllArgsConstructor
@Log4j2
public class DataTypeController {
	@Autowired
	DataTypeService dataTypeService;
	
	


	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@PostMapping("/create")
	public BaseDTO create(@Valid @RequestBody DataTypeDTO dataTypeDTO) {
		//log.info("<--- DataTypeController create  STARTED --->");
		BaseDTO response = dataTypeService.create(dataTypeDTO);
		//log.info("<--- Corporation create  END --->");
		return response;
	}
//	@PreAuthorize("#oauth2.hasScope('Admin')")
	@GetMapping("/getall")
	public BaseDTO getAll() {
		//log.info("<--- DataTypeController getAll  STARTED --->");
		BaseDTO response = dataTypeService.getAll();
		//log.info("<--- DataTypeController getAll  END --->");
		return response;
	}
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@PutMapping("/update/{id}")
	public BaseDTO update(@PathVariable UUID id,@Valid @RequestBody DataTypeDTO dataTypeDTO) {
		//log.info("<--- DataTypeController update  STARTED --->");
		BaseDTO response = dataTypeService.update(id, dataTypeDTO);
		//log.info("<--- DataTypeController update  END --->");
		return response;
	}

	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@GetMapping("/get/{id}")
	public BaseDTO getById(@PathVariable("id") UUID id) {
		//log.info("<--- DataTypeController getById  STARTED --->");
		BaseDTO response = dataTypeService.getById(id);
		//log.info("<--- Corporation getById  END --->");
		return response;
	}
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@DeleteMapping("/delete/{id}")
	public BaseDTO delete(@PathVariable("id") UUID id) {
		//log.info("<--- DataTypeController delete  STARTED --->");
		BaseDTO response = dataTypeService.delete(id);
		//log.info("<--- DataTypeController delete END --->");
		return response;
	}
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@DeleteMapping("/softdelete/{id}")
	public BaseDTO softDelete(@PathVariable("id") UUID id) {
		//log.info("<--- DataTypeController delete STARTED --->");
		BaseDTO response = dataTypeService.softDelete(id);
		//log.info("<--- DataTypeController delete END --->");
		return response;
	}
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@GetMapping("/getallactive")
	public BaseDTO getAllActive() {
		//log.info("<--- DataTypeController getAllActive  STARTED --->");
		BaseDTO response = dataTypeService.getAllActive();
		//log.info("<--- DataTypeController getAllActive  END --->");
		return response;
	}
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@PostMapping("/lazylist")
	public BaseDTO getLazyList(@RequestBody PaginationRequestDTO requestData) {
		BaseDTO response = new BaseDTO();
		//log.info("<== Started dataTypeService.getLazyList() ==>");
		response = dataTypeService.getLazyList(requestData);
		//log.info("<== Ended dataTypeService.getLazyList() ==>");
		return response;
	}
	


}
