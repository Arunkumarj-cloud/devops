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
import com.oasys.uppcl_user__master_management.dto.StatusMasterDTO;
import com.oasys.uppcl_user__master_management.service.StatusMasterService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/status")
@Log4j2
public class StatusMasterController {
	
	@Autowired
	StatusMasterService statusMasterService;
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@PostMapping("/create")
	public BaseDTO create( @RequestBody StatusMasterDTO statusMasterDTO) {
		//log.info("<---StatusMasterController.create STARTED --->");
		BaseDTO response = statusMasterService.create(statusMasterDTO);
		//log.info("<--- StatusMasterController. create END --->");
		return response;
	}
	
	/*
	 * @GetMapping("/get/{id}") public BaseDTO getById(@PathVariable("id") UUID id)
	 * { //log.info("<--- StatusMasterController getById  STARTED --->"); BaseDTO
	 * response = statusMasterService.getById(id);
	 * //log.info("<--- StatusMasterController getById  END --->"); return response;
	 * }
	 */
	
	
	@GetMapping("/get/{id}") public BaseDTO getById(@PathVariable("id") UUID id)
	  { //log.info("<--- StatusMasterController getById  STARTED --->"); BaseDTO
		 BaseDTO response = statusMasterService.getById(id);
	  //log.info("<--- StatusMasterController getById  END --->");
		 return response;
	  }
	 
	 @GetMapping("/getall")
		public BaseDTO getAll() {
			//log.info("<--- StatusMasterController getAll  STARTED --->");
			BaseDTO response = statusMasterService.getAll();
			//log.info("<--- StatusMasterController getAll  END --->");
			return response;
		}
		
		//@PreAuthorize("#oauth2.hasScope('Admin')")
		@PostMapping("/lazylist")
		public BaseDTO getLazyList(@RequestBody PaginationRequestDTO requestData) {
		BaseDTO response = new BaseDTO();
		//log.info("<--- StatusMasterController lazylist STARTED --->");
		response = statusMasterService.getLazyList(requestData);
		//log.info("<--- StatusMasterController lazylist END --->");
		return response;
		}
		
		//@PreAuthorize("#oauth2.hasScope('Admin')")
		@PutMapping("/update/{id}")
		public BaseDTO update(@PathVariable UUID id,  @RequestBody StatusMasterDTO statusMasterDTO) {
			//log.info("<== Started StatusMasterController.update() ==>");
			BaseDTO response = new BaseDTO();
			response = statusMasterService.update(id, statusMasterDTO);
			//log.info("<== Ended StatusMasterController.update() ==>");
			return response;
		}
		
		//@PreAuthorize("#oauth2.hasScope('Admin')")
		@GetMapping("/getallactive")
		public BaseDTO getAllActive() {
			//log.info("<== Started StatusMasterController.getAllActive() ==>");
			BaseDTO response = statusMasterService.getAllActive();
			//log.info("<== Ended StatusMasterController.getAllActive() ==>");
			return response;
		}

		//@PreAuthorize("#oauth2.hasScope('Admin')")
		@DeleteMapping("/softdelete/{id}")
		public BaseDTO softDelete(@PathVariable("id") UUID id) {
			//log.info("<--- Started StatusMasterController  softDelete  STARTED --->");
			BaseDTO baseDTO = statusMasterService.softDelete(id);
			//log.info("<---  StatusMasterController  softDelete  END --->");
			return baseDTO;
		}


}
