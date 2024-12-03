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
import com.oasys.uppcl_user__master_management.dto.WardMasterDTO;
import com.oasys.uppcl_user__master_management.service.WardMasterService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/ward")
public class WardMasterController {
	
	@Autowired
	WardMasterService wardMasterService;


	
	@PostMapping("/create")
	public BaseDTO addWard(@Valid @RequestBody WardMasterDTO wardMasterDTO) {
		//log.info("<-- WardMasterController.addWard() ---> Started");
		BaseDTO response = wardMasterService.create(wardMasterDTO);
		//log.info("<-- WardMasterController.addWard() ---> Ended");
		return response;
	}

	
	
	@DeleteMapping("/delete/{id}")
	public BaseDTO deleteWard(@PathVariable UUID id) {
		//log.info("<-- WardMasterController.deleteWard()---> Started");
		BaseDTO response = wardMasterService.delete(id);
		//log.info("<-- WardMasterController.deleteWard()---> Ended");
		return response;
	}

	
	@GetMapping("/get/{id}")
	public BaseDTO getById(@PathVariable UUID id) {
		//log.info("<-- WardMasterController.getone()---> Started");
		BaseDTO response = wardMasterService.getById(id);
		//log.info("<-- WardMasterController.getone()---> Ended");
		return response;
	}

	
	
	@PutMapping("/update/{id}")
	public BaseDTO update(@PathVariable UUID id,@Valid @RequestBody WardMasterDTO wardMasterDTO) {
		//log.info("<-- WardMasterController.putone()---> Started");
		BaseDTO response = wardMasterService.update(id, wardMasterDTO);
		//log.info("<-- WardMasterController.putone()---> Ended");
		return response;
	}

	
	@GetMapping("/getAll")
	public BaseDTO getAll() {
		//log.info("<-- WardMasterController.getall()---> Started");
		BaseDTO response = wardMasterService.getAll();
		//log.info("<-- WardMasterController.getall()---> Ended");
		return response;
	}

	
	@PostMapping("/lazylist")
	public BaseDTO getLazyList(@RequestBody PaginationRequestDTO requestData) {
		BaseDTO response = new BaseDTO();
		//log.info("<-- WardMasterController.getLazyList()---> Started");
		response = wardMasterService.getLazyList(requestData);
		//log.info("<-- WardMasterController.getLazyList()---> Ended");
		return response;
	}

	
	@GetMapping("/getallactive")
	public BaseDTO getAllActive() {
		//log.info("<-- WardMasterController.getallactive()---> Started");
		BaseDTO response = wardMasterService.getAllActive();
		//log.info("<-- WardMasterController.getallactive()---> Ended");
		return response;
	}

	
	@DeleteMapping("/softdelete/{id}")
	public BaseDTO softDelete(@PathVariable("id") UUID id) {
		//log.info("<--- WardMasterController.softDelete() STARTED --->");
		BaseDTO response = wardMasterService.softDelete(id);
		//log.info("<--- WardMasterController.softDelete() END --->");
		return response;
	}
}
