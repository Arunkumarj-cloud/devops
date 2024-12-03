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
import com.oasys.uppcl_user__master_management.dto.VillageMasterDTO;
import com.oasys.uppcl_user__master_management.service.VillageMasterService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/village")
@Log4j2
public class VillageMasterController {
	@Autowired
	VillageMasterService villageService;
	
	
	@PostMapping("/create")
	public BaseDTO create(@Valid @RequestBody VillageMasterDTO villageMasterDTO) {
		//log.info("<--- Started VillageMasterController.create --->");
		BaseDTO baseDTO = villageService.create(villageMasterDTO);
		//log.info("<--- Ended VillageMasterController.create --->");
		return baseDTO;
	}
	
	
	@GetMapping("/getAll")
	public BaseDTO getAll() {
		//log.info("<--- Started VillageMasterController.getAll --->");
		BaseDTO baseDTO = villageService.getAll();
		//log.info("<--- Ended VillageMasterController.getAll --->");
		return baseDTO;
	}
	
	
	@GetMapping("/getallactive")
	public BaseDTO getAllActive() {
		//log.info("<--- VillageMaster getAll Controller STARTED --->");
		BaseDTO response = villageService.getAllActive();
		//log.info("<--- VillageMaster getAll Controller END --->");
		return response;
	}
	
	
	@PutMapping("/update/{id}")
	public BaseDTO update(@PathVariable("id") UUID id,@Valid @RequestBody VillageMasterDTO villageMasterDTO) {
		//log.info("<--- Started VillageMasterController.update --->");
		BaseDTO baseDTO = villageService.update(id, villageMasterDTO);
		//log.info("<--- Ended VillageMasterController.update --->");
		return baseDTO;
	}
	
	
	@GetMapping("/get/{id}")
	public BaseDTO getById(@PathVariable("id") UUID id) {
		//log.info("<--- Started VillageMasterController.update --->");
		BaseDTO baseDTO = villageService.getById(id);
		//log.info("<--- Ended VillageMasterController.update --->");
		return baseDTO;
	}
	
	
	@DeleteMapping("/delete/{id}")
	public BaseDTO delete(@PathVariable("id") UUID id) {
		//log.info("<--- Started VillageMasterController.delete --->");
		BaseDTO baseDTO = villageService.delete(id);
		//log.info("<--- Ended VillageMasterController.delete --->");
		return baseDTO;
	}
	
	
	@PostMapping("/lazylist")
	public BaseDTO getLazyList(@RequestBody PaginationRequestDTO requestData) {
		BaseDTO baseDTO = new BaseDTO();
		//log.info("<--- VillageMasterController getById Controller STARTED --->");
		baseDTO = villageService.getLazyList(requestData);
		//log.info("<--- VillageMasterController getById Controller END --->");
		return baseDTO;
	}
	
	
	@PutMapping("/softdelete/{id}")
	public BaseDTO softDelete(@PathVariable("id") UUID id) {
		//log.info("<--- VillageMasterController delete Controller STARTED --->");
		BaseDTO baseDTO = villageService.softDelete(id);
		//log.info("<--- VillageMasterController delete Controller END --->");
		return baseDTO;
	}
	
	
	@GetMapping("/getByTalukId/{id}")
	public BaseDTO getByTalukId(@PathVariable("id") UUID id) {
		//log.info("<--- Started TalukMaster.getByTalukId --->");
		BaseDTO baseDTO = villageService.getByTalukId(id);
		//log.info("<--- Ended TalukMaster.getByTalukId --->");
		return baseDTO;
	}
	
	
}
