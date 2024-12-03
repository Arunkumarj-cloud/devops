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
import com.oasys.uppcl_user__master_management.dto.FaqDTO;
import com.oasys.uppcl_user__master_management.dto.FaqRequestDTO;
import com.oasys.uppcl_user__master_management.service.FaqService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/faq")
@Log4j2
public class FaqController {
	@Autowired(required = false)
	FaqService faqService;

//	@PreAuthorize(PrivilegeConstant.ADD_FAQ_LIST)
	@PostMapping("/create")
	public BaseDTO create(@Valid @RequestBody FaqDTO faqDTO) {	
		//log.info("<--- Faq create Controller STARTED --->");
		BaseDTO response = faqService.create(faqDTO);
		//log.info("<--- Faq create Controller END --->");
		return response;
	}
//	@PreAuthorize("#oauth2.hasScope('Admin')")
	@PostMapping("/createList")
	public BaseDTO createList(@RequestBody FaqRequestDTO faqRequestDTO) {
		//log.info("<--- Faq createList Controller STARTED --->");
		BaseDTO response = faqService.createList(faqRequestDTO.getList());
		//log.info("<--- Faq createList Controller END --->");
		return response;
	}
	
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/FAQGetAll")
	public BaseDTO FAQGetAll() {
		BaseDTO response = new BaseDTO();
		response = faqService.FAQGetAll();
		return response;
	}
		
	//@PreAuthorize(PrivilegeConstant.UPDATE_FAQ_LIST)
	@PostMapping("/updateList")
	public BaseDTO updateList(@RequestBody FaqRequestDTO faqDTO) {
		//log.info("<--- Faq updateList Controller STARTED --->");
		BaseDTO response = faqService.updateList(faqDTO.getList());
		//log.info("<--- Faq updateList Controller END --->");
		return response;
	}

	//@PreAuthorize("#oauth2.hasScope('Admin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/getall")
	public BaseDTO getAll() {
		//log.info("<--- Faq getAll Controller STARTED --->");
		BaseDTO response = faqService.getAll();
		//log.info("<--- Faq getAll Controller END --->");
		return response;
	}

	//@PreAuthorize("#oauth2.hasScope('Admin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/getAllActive")
	public BaseDTO getAllActive() {
		//log.info("<--- Faq getAllActive Controller STARTED --->");
		BaseDTO response = faqService.getAllActive();
		//log.info("<--- Faq getAllActive Controller END --->");
		return response;
	}

	//@PreAuthorize("#oauth2.hasScope('Admin') or #oauth2.hasScope('Merchant')")
	@GetMapping("/getByCategoryId/{id}")
	public BaseDTO getByCategoryId(@PathVariable("id") UUID id) {
		//log.info("<--- Faq getAll Faq ByCategoryId Controller STARTED --->");
		BaseDTO response = faqService.getByCategoryId(id);
		//log.info("<--- Faq getAll Faq ByCategoryId Controller END --->");
		return response;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin') or #oauth2.hasScope('Merchant')")
	@GetMapping("/getByCategoryId/{id}/{language}")
	public BaseDTO getByCategoryIdLang(@PathVariable("id") UUID id, @PathVariable("language") String language) {
		// log.info("<--- Faq getAll Faq ByCategoryId Controller STARTED --->");
		BaseDTO response = faqService.getByCategoryIdLang(id, language);
		// log.info("<--- Faq getAll Faq ByCategoryId Controller END --->");
		return response;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin') or #oauth2.hasScope('Merchant')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/getbyid/{id}")
	public BaseDTO getDetailsById(@PathVariable("id") UUID id) {
		//log.info("<--- Faq  Controller getDetailsById Controller STARTED --->");
		BaseDTO response = faqService.getDetailsById(id);
		//log.info("<--- Faq Controller getDetailsById Controller END --->");
		return response;
	}

	//@PreAuthorize("#oauth2.hasScope('Admin')")
//	@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@PostMapping("/lazyList")
	public BaseDTO LazyList(@RequestBody PaginationRequestDTO paginationRequestDTO) {
		//log.info("<--- Faq LazyList By Faq Type Controller STARTED --->");
		BaseDTO response = faqService.LazyList(paginationRequestDTO);
		//log.info("<--- Faq LazyList By Faq Type Controller END --->");
		return response;
	}

	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@DeleteMapping("/delete/{id}")
	public BaseDTO deleteById(@PathVariable UUID id) {
		//log.info("<--- Faq controller DeleteById STARTED --->");
		BaseDTO response = faqService.deleteById(id);
		//log.info("<--- Faq controller DeleteById END --->");
		return response;
	}

	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@PostMapping("/categoryLazyList")
	public BaseDTO CategoryLazyList(@RequestBody PaginationRequestDTO paginationRequestDTO) {
		//log.info("<--- Faq controller CategoryLazyList STARTED --->");
		BaseDTO response = faqService.CategoryLazyList(paginationRequestDTO);
		//log.info("<--- Faq controller CategoryLazyList END --->");
		return response;
	}

	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@PutMapping("/softdelete/{id}")
	public BaseDTO softDelete(@PathVariable("id") UUID id) {
		//log.info("<--- Faq controller  softDelete  STARTED --->");
		BaseDTO baseDTO = faqService.softDelete(id);
		//log.info("<--- Faq controller  softDelete  END --->");
		return baseDTO;
	}

	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@PostMapping("/softdelete/all")
	public BaseDTO softDeleteAll(@RequestBody FaqRequestDTO listOfIds) {
		//log.info("<--- Faq controller  softDelete  STARTED --->");
		BaseDTO baseDTO = faqService.softDeleteAll(listOfIds);
		//log.info("<--- Faq controller  softDelete  END --->");
		return baseDTO;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@PutMapping("/update/{id}")
	public BaseDTO updateFaq(@RequestBody FaqDTO request ,  @PathVariable("id") UUID id) {
		//log.info("<--- Faq controller  softDelete  STARTED --->");
		BaseDTO baseDTO = faqService.updateFaq(id,request);
		//log.info("<--- Faq controller  softDelete  END --->");
		return baseDTO;
	}
}



