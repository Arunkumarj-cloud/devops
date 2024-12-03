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
import com.oasys.uppcl_user__master_management.dto.ComplaintReasonDTO;
import com.oasys.uppcl_user__master_management.service.ComplaintReasonService;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping("/complaint")
public class ComplaintReasonController {
	@Autowired
	ComplaintReasonService complaintReasonService;

	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@PostMapping("/create")
	public BaseDTO create(@Valid @RequestBody ComplaintReasonDTO complaintReasonDTO) {
		//log.info("<--- ComplaintReasonController.create() STARTED --->");
		BaseDTO response = complaintReasonService.create(complaintReasonDTO);
		//log.info("<--- ComplaintReasonController.create() END --->");
		return response;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	
	@GetMapping("/getall")
	public BaseDTO getAll() {
		//log.info("<--- ComplaintReasonController.getAll()  STARTED --->");
		BaseDTO response = complaintReasonService.getAll();
		//log.info("<--- ComplaintReasonController.getAll() ENDED --->");
		return response;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@GetMapping("/getallactive")
	public BaseDTO getAllActive() {
		//log.info("<--- ComplaintReasonController.getAllActive() STARTED --->");
		BaseDTO response = complaintReasonService.getAllActive();
		//log.info("<--- ComplaintReasonController.getAllActive() ENDED --->");
		return response;
	}

	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@PutMapping("/update/{id}")
	public BaseDTO update(@PathVariable UUID id, @Valid @RequestBody ComplaintReasonDTO complaintReasonDTO) {
		//log.info("<--- ComplaintReasonController.update() STARTED --->");
		BaseDTO response = complaintReasonService.update(id,complaintReasonDTO);
		//log.info("<--- ComplaintReasonController.update() ENDED --->");
		return response;
	}
		
//	@PreAuthorize("#oauth2.hasScope('Admin')")
	@PostMapping("/lazylist")
	public BaseDTO getLazyList(@RequestBody PaginationRequestDTO requestData) {
 	BaseDTO response = new BaseDTO();
	//log.info("<--- ComplaintReasonController.getLazyList() STARTED --->");
	response = complaintReasonService.getLazyList(requestData);
	//log.info("<--- ComplaintReasonController.getLazyList() ENDED --->");
	return response;
	}

	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@GetMapping("/get/{id}")
	public BaseDTO getById(@PathVariable("id") UUID id) {
		//log.info("<--- ComplaintReasonController.getById() STARTED --->");
		BaseDTO response = complaintReasonService.getById(id);
		//log.info("<--- ComplaintReasonController.getById() ENDED --->");
		return response;
	}
 
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@DeleteMapping("/delete/{id}")
	public BaseDTO delete(@PathVariable("id") UUID id) {
		//log.info("<--- ComplaintReasonController.delete() STARTED --->");
		BaseDTO response = complaintReasonService.delete(id);
		//log.info("<--- ComplaintReasonController.delete() ENDED --->");
		return response;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@DeleteMapping("/softdelete/{id}")
	public BaseDTO softDelete(@PathVariable("id") UUID id) {
		//log.info("<--- ComplaintReasonController.softDelete() STARTED --->");
		BaseDTO response = complaintReasonService.softDelete(id);
		//log.info("<--- ComplaintReasonController.softDelete() ENDED --->");
		return response;
	}

}
