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
import com.oasys.uppcl_user__master_management.dto.CorporationDTO;
import com.oasys.uppcl_user__master_management.service.CorporationService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/corporation")
@Log4j2
public class CorporationController {

	@Autowired
	CorporationService corporationService;
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@PostMapping("/create")
	public BaseDTO create(@Valid @RequestBody CorporationDTO corporationDTO) {
		//log.info("<--- Corporation create Controller STARTED --->");
		BaseDTO response = corporationService.create(corporationDTO);
		//log.info("<--- Corporation create Controller END --->");
		return response;
	}

//	@PreAuthorize("#oauth2.hasScope('Admin')")
	@GetMapping("/getall")
	public BaseDTO getAll() {
		//log.info("<--- Corporation getAll Controller STARTED --->");
		BaseDTO response = corporationService.getAll();
		//log.info("<--- Corporation getAll Controller END --->");
		return response;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@GetMapping("/getallactive")
	public BaseDTO getAllActive() {
		//log.info("<--- Corporation getAll Controller STARTED --->");
		BaseDTO response = corporationService.getAllActive();
		//log.info("<--- Corporation getAll Controller END --->");
		return response;
	}

	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@PutMapping("/update/{id}")
	public BaseDTO update(@PathVariable UUID id, @Valid @RequestBody CorporationDTO corporationDTO) {
		//log.info("<--- Corporation update Controller STARTED --->");
		BaseDTO response = corporationService.update(id,corporationDTO);
		//log.info("<--- Corporation update Controller END --->");
		return response;
	}
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@PostMapping("/lazylist")
	public BaseDTO getLazyList(@RequestBody PaginationRequestDTO requestData) {
	BaseDTO response = new BaseDTO();
	//log.info("<--- Corporation getById Controller STARTED --->");
	response = corporationService.getLazyList(requestData);
	//log.info("<--- Corporation getById Controller END --->");
	return response;
	}

	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@GetMapping("/get/{id}")
	public BaseDTO getById(@PathVariable("id") UUID id) {
		//log.info("<--- Corporation getById Controller STARTED --->");
		BaseDTO response = corporationService.getById(id);
		//log.info("<--- Corporation getById Controller END --->");
		return response;
	}

	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@DeleteMapping("/delete/{id}")
	public BaseDTO delete(@PathVariable("id") UUID id) {
		//log.info("<--- Corporation delete Controller STARTED --->");
		BaseDTO response = corporationService.delete(id);
		//log.info("<--- Corporation delete Controller END --->");
		return response;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@PutMapping("/softdelete/{id}")
	public BaseDTO softDelete(@PathVariable("id") UUID id) {
		//log.info("<--- Corporation delete Controller STARTED --->");
		BaseDTO response = corporationService.softDelete(id);
		//log.info("<--- Corporation delete Controller END --->");
		return response;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@GetMapping("/getByDistrictId/{id}")
	public BaseDTO getbyDistrictId(@PathVariable("id") UUID id) {
		//log.info("<--- Corporation getbyDistrictId Controller STARTED --->");
		BaseDTO response = corporationService.getbyDistrictId(id);
		//log.info("<--- Corporation getbyDistrictId Controller END --->");
		return response;
	}

}
