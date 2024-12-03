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
import com.oasys.uppcl_user__master_management.dto.TermsAndConditionsDTO;
import com.oasys.uppcl_user__master_management.service.TermsAndConditionsService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/termsandconditions")
@Log4j2
public class TermsAndConditionsController {
	
	@Autowired
	TermsAndConditionsService termsAndConditionsService; 
	
	
	@PostMapping("/create")
	public BaseDTO create(@Valid @RequestBody TermsAndConditionsDTO termsAndConditionsDTO) {
		//log.info("<== Started TermsAndConditionsController.create ==>");
		BaseDTO baseDTO = new BaseDTO();
		baseDTO = termsAndConditionsService.create(termsAndConditionsDTO);
		//log.info("<== Ended TermsAndConditionsController.create ==>");
		return baseDTO;
	}
	
	@GetMapping("/getall")
	public BaseDTO getAll() {
		//log.info("<--- TermsAndConditions getAll Controller STARTED --->");
		BaseDTO baseDTO = termsAndConditionsService.getAll();
		//log.info("<--- TermsAndConditions getAll Controller END --->");
		return baseDTO;
	}
	
	
	@GetMapping("/getallactive")
	public BaseDTO getAllActive() {
		//log.info("<--- TermsAndConditions getAll active Controller STARTED --->");
		BaseDTO baseDTO = termsAndConditionsService.getAllActive();
		//log.info("<--- TermsAndConditions getAll active Controller END --->");
		return baseDTO;
	}
	
	
	@PutMapping("/update/{id}")
	public BaseDTO update(@PathVariable("id") UUID id, @Valid @RequestBody TermsAndConditionsDTO termsAndConditionsDTO) {
		//log.info("<--- TermsAndConditions update Controller STARTED --->");
		BaseDTO baseDTO = termsAndConditionsService.update(id, termsAndConditionsDTO);
		//log.info("<--- TermsAndConditions update Controller END --->");
		return baseDTO;
	}

	
	@PostMapping("/lazylist")
	public BaseDTO getLazyList(@RequestBody PaginationRequestDTO requestData) {
		BaseDTO baseDTO = new BaseDTO();
		//log.info("<--- TermsAndConditions lazylist Controller STARTED --->");
		baseDTO = termsAndConditionsService.getLazyList(requestData);
		//log.info("<--- TermsAndConditions lazylist Controller END --->");
		return baseDTO;
	}

	
	@GetMapping("/get/{id}")
	public BaseDTO getById(@PathVariable("id") UUID id) {
		//log.info("<--- TermsAndConditions getById Controller STARTED --->");
		BaseDTO baseDTO = termsAndConditionsService.getById(id);
		//log.info("<--- TermsAndConditions getById Controller END --->");
		return baseDTO;
	}

	
	@DeleteMapping("/delete/{id}")
	public BaseDTO delete(@PathVariable("id") UUID id) {
		//log.info("<--- TermsAndConditions delete Controller STARTED --->");
		BaseDTO baseDTO = termsAndConditionsService.delete(id);
		//log.info("<--- TermsAndConditions delete Controller END --->");
		return baseDTO;
	}

	
	@DeleteMapping("/softdelete/{id}")
	public BaseDTO softDelete(@PathVariable("id") UUID id) {
		//log.info("<--- TermsAndConditions softdelete Controller STARTED --->");
		BaseDTO response = termsAndConditionsService.softDelete(id);
		//log.info("<--- TermsAndConditions softdelete Controller ENDED --->");
		return response;
	}

}
