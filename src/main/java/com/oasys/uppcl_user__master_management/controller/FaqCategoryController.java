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
import com.oasys.uppcl_user__master_management.dto.FaqCategoryDTO;
import com.oasys.uppcl_user__master_management.service.EducationService;
import com.oasys.uppcl_user__master_management.service.FaqCategoryService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/faqCategory")
@AllArgsConstructor
@Log4j2
public class FaqCategoryController {
	@Autowired
	FaqCategoryService categoryService;
	
	//@PreAuthorize(PrivilegeConstant.ADD_FAQ_CATEGORY)
	@PostMapping("/create")
	public BaseDTO create(@Valid @RequestBody FaqCategoryDTO faqCategoryDTO) {
		//log.info("<--- Faq Category create Controller STARTED --->");
		BaseDTO response = categoryService.create(faqCategoryDTO);
		//log.info("<--- Faq Category create Controller END --->");
		return response;
	}
//	@PreAuthorize(PrivilegeConstant.UPDATE_FAQ_CATEGORY)
	@PutMapping("/update/{id}")
	public BaseDTO update(@PathVariable("id") UUID id, @Valid @RequestBody FaqCategoryDTO faqCategoryDTO) {
		//log.info("<--- Faq Category update Controller STARTED --->");
		BaseDTO response = categoryService.update(id,faqCategoryDTO);
		//log.info("<--- Faq Category update Controller END --->");
		return response;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/getById/{id}")
	public BaseDTO getById(@PathVariable("id") UUID id) {
		//log.info("<--- Faq Category getById Controller STARTED --->");
		BaseDTO response = categoryService.getByID(id);
		//log.info("<--- Faq Category getById Controller END --->");
		return response;
	}
	
//	@PreAuthorize("#oauth2.hasScope('Admin')")
	@DeleteMapping("/delete/{id}")
	public BaseDTO delete(@PathVariable("id") UUID id) {
		//log.info("<--- Faq Category delete Controller STARTED --->");
		BaseDTO response = categoryService.delete(id);
		//log.info("<--- Faq Category delete Controller END --->");
		return response;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/getAll")
	public BaseDTO getAll() {
		//log.info("<--- Faq Category getAll Controller STARTED --->");
		BaseDTO response = categoryService.getAll();
		//log.info("<--- Faq Category getAll Controller END --->");
		return response;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')  or #oauth2.hasScope('Merchant')")
	@GetMapping("/getAllActive")
	public BaseDTO getAllActive() {
		//log.info("<--- Faq Category getAllActive Controller STARTED --->");
		BaseDTO response = categoryService.getAllActive();
		//log.info("<--- Faq Category getAllActive Controller END --->");
		return response;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@PostMapping("/lazylist")
	public BaseDTO lazylist(@RequestBody PaginationRequestDTO paginationRequestDTO) {
		//log.info("<--- Faq Category lazylist Controller STARTED --->");
		BaseDTO response = categoryService.lazylist(paginationRequestDTO);
		//log.info("<--- Faq Category lazylist Controller END --->");
		return response;
	}

//	@PreAuthorize("#oauth2.hasScope('Admin')")
	@PutMapping("/softdelete/{id}")
	public BaseDTO softDelete(@PathVariable("id") UUID id) {
		//log.info("<--- Started FaqCategoryMasterController  softDelete  STARTED --->");
		BaseDTO baseDTO = categoryService.softDelete(id);
		//log.info("<---  FaqCategoryMasterController  softDelete  END --->");
		return baseDTO;
	}
}



