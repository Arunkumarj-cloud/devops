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
import com.oasys.uppcl_user__master_management.dto.LanguageDTO;
import com.oasys.uppcl_user__master_management.service.LanguageService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/language")
@Log4j2
public class LanguageController {
	@Autowired
	LanguageService languageService;

	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@PostMapping("/create")
	public BaseDTO createLanguage(@Valid @RequestBody LanguageDTO languageDTO) {
		//log.info("<--- Language create Controller STARTED --->");
		BaseDTO response = languageService.create(languageDTO);
		//log.info("<--- Language create Controller END --->");
		return response;
	}

	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@GetMapping("/getall")
	public BaseDTO getAll() {
		//log.info("<--- Language getAll Controller STARTED --->");
		BaseDTO response =languageService.getAll();
		//log.info("<--- Language getAll Controller END --->");
		return response;
	}
	//@PreAuthorize("#oauth2.hasScope('Admin') or #oauth2.hasScope('Sales Officer') or #oauth2.hasScope('Customer Support Officer') or #oauth2.hasScope('Finance Officer')")
	@GetMapping("/getallactive")
	public BaseDTO getAllActive() {
		//log.info("<--- Language getAll Controller STARTED --->");
		BaseDTO response = languageService.getAllActive();
		//log.info("<--- Language getAll Controller END --->");
		return response;
	}

	@PutMapping("/update/{id}")
	public BaseDTO updateLanguage(@PathVariable("id") UUID id,@Valid @RequestBody LanguageDTO languageDTO) {
		//log.info("<--- Language update Controller STARTED --->");
		BaseDTO response = languageService.update(id,languageDTO);
		//log.info("<--- Language update Controller END --->");
		return response;
	}
		
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@PostMapping("/lazylist")
	public BaseDTO getLazyList(@RequestBody PaginationRequestDTO requestData) {
	BaseDTO response = new BaseDTO();
	//log.info("<--- Language lazylist Controller STARTED --->");
	response = languageService.getLazyList(requestData);
	//log.info("<--- Language lazylist Controller END --->");
	return response;
	}

	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@GetMapping("/get/{id}")
	public BaseDTO getById(@PathVariable("id") UUID id) {
		//log.info("<--- Language getById Controller STARTED --->");
		BaseDTO response = languageService.getById(id);
		//log.info("<--- Language getById Controller END --->");
		return response;
	}

	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@DeleteMapping("/delete/{id}")
	public BaseDTO delete(@PathVariable("id") UUID id) {
		//log.info("<--- Language delete Controller STARTED --->");
		BaseDTO response = languageService.delete(id);
		//log.info("<--- Language delete Controller END --->");
		return response;
	}
	
	///@PreAuthorize("#oauth2.hasScope('Admin')")
	@PutMapping("/softdelete/{id}")
	public BaseDTO softDelete(@PathVariable("id") UUID id) {
		//log.info("<--- Language softdelete Controller STARTED --->");
		BaseDTO response = languageService.softDelete(id);
		//log.info("<--- Language softdelete Controller END --->");
		return response;
	}

}
