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
import com.oasys.uppcl_user__master_management.dto.StatusTypeMasterDTO;
import com.oasys.uppcl_user__master_management.service.StatusTypeMasterService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/statusType")
@Log4j2
public class StatusTypeMasterController {
	
	@Autowired
    StatusTypeMasterService statusTypeMasterService;

	
	@PostMapping("/create")
	public BaseDTO create( @RequestBody StatusTypeMasterDTO statusTypeMasterDTO) {
		//log.info("<---StatusTypeMasterController.create STARTED --->");
		BaseDTO response = statusTypeMasterService.create(statusTypeMasterDTO);
		//log.info("<--- StatusMasterController. create END --->");
		return response;
	}
	
	@GetMapping("/get/{id}")
	public BaseDTO getById(@PathVariable("id") UUID id) {
		//log.info("<--- StatusTypeMasterController getById  STARTED --->");
		BaseDTO response = statusTypeMasterService.getById(id);
		//log.info("<--- StatusTypeMasterController getById  END --->");
		return response;
	}	
	
	@GetMapping("/getall")
	public BaseDTO getAll() {
		//log.info("<--- StatusTypeMasterController getAll  STARTED --->");
		BaseDTO response = statusTypeMasterService.getAll();
		//log.info("<--- StatusTypeMasterController getAll  END --->");
		return response;
	}
	
	@PostMapping("/lazylist")
	public BaseDTO getLazyList(@RequestBody PaginationRequestDTO requestData) {
	BaseDTO response = new BaseDTO();
	//log.info("<--- StatusTypeMasterController lazylist STARTED --->");
	response = statusTypeMasterService.getLazyList(requestData);
	//log.info("<--- StatusTypeMasterController lazylist END --->");
	return response;
	}
	
	@PutMapping("/update/{id}")
	public BaseDTO update(@PathVariable UUID id,  @RequestBody StatusTypeMasterDTO statusTypeMasterDTO) {
		//log.info("<== Started StatusTypeMasterController.update() ==>");
		BaseDTO response = new BaseDTO();
		response = statusTypeMasterService.update(id, statusTypeMasterDTO);
		//log.info("<== Ended StatusTypeMasterController.update() ==>");
		return response;
	}
	
	
	@GetMapping("/getallactive")
	public BaseDTO getAllActive() {
		//log.info("<== Started StatusTypeMasterController.getAllActive() ==>");
		BaseDTO response = statusTypeMasterService.getAllActive();
		//log.info("<== Ended StatusTypeMasterController.getAllActive() ==>");
		return response;
	}

	
	@DeleteMapping("/softdelete/{id}")
	public BaseDTO softDelete(@PathVariable("id") UUID id) {
		//log.info("<--- Started StatusTypeMasterController  softDelete  STARTED --->");
		BaseDTO baseDTO = statusTypeMasterService.softDelete(id);
		//log.info("<---  StatusTypeMasterController  softDelete  END --->");
		return baseDTO;
	}
	
	

}
