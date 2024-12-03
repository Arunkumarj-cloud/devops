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
import com.oasys.uppcl_user__master_management.dto.TemplateDTO;
import com.oasys.uppcl_user__master_management.service.TemlpateService;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping("/template")
public class TemplateController {

	@Autowired
	TemlpateService templateService;

	
	@PostMapping("/create")
	public BaseDTO createTemplate(@Valid @RequestBody TemplateDTO templateDTO) {
		//log.info("<----------Started TemplateController.createTemplate()-----> ");
		BaseDTO response = templateService.createTemplate(templateDTO);
		//log.info(" <----------Ended TemplateController.createTemplate()----->  ");
		return response;

	}

	
	@GetMapping("/getall")
	public BaseDTO getAll() {
		//log.info("<----------Started TemplateController.getAll()-----> ");
		BaseDTO response = new BaseDTO();
		response = templateService.getAll();
		//log.info(" <----------Ended TemplateController.getAll()----->  ");
		return response;

	}
	

	
	@GetMapping("/get")
	public BaseDTO get() {
		BaseDTO response = new BaseDTO();
		response = templateService.get();
		return response;

	}

	
	@PutMapping("/update/{id}")
	public BaseDTO update(@PathVariable("id") UUID id, @RequestBody TemplateDTO templateDTO) {
		//log.info("<----------Started TemplateController.update()-----> ");
		BaseDTO response = templateService.update(id, templateDTO);
		//log.info(" <----------Ended TemplateController.update()----->  ");
		return response;
	}

	
	@GetMapping("/get/{id}")
	public BaseDTO getById(@PathVariable("id") UUID id) {
		//log.info("<----------Started TemplateController.getById()-----> ");
		BaseDTO response = templateService.getById(id);
		//log.info(" <----------Ended TemplateController.getById()----->  ");
		return response;
	}

	
	@GetMapping("/getbyname/{name}")
	public BaseDTO getByName(@PathVariable("name") String name) {
		//log.info("<----------Started TemplateController.getById()-----> ");
		BaseDTO response = templateService.getByName(name);
		//log.info(" <----------Ended TemplateController.getById()----->  ");
		return response;
	}

	
	@PostMapping("/lazylist1")
	public BaseDTO lazyList(@Valid @RequestBody PaginationRequestDTO pageData) {
		//log.info("<----------Started TemplateController.lazyList()-----> ");
		BaseDTO response = new BaseDTO();
		response = templateService.lazyList(pageData);
		//log.info("<----------Ended TemplateController.lazyList()-----> ");
		return response;
	}
	
	
	@PostMapping("/lazylist")
	public BaseDTO lazyList1(@Valid @RequestBody PaginationRequestDTO pageData) {
		BaseDTO response = new BaseDTO();
		response = templateService.lazyList1(pageData);
		return response;
	}

	
	@GetMapping("/getallactive")
	public BaseDTO getAllActive() {
		//log.info("<== Started TemplateController.getAllActive() ==>");
		BaseDTO response = templateService.getAllActive();
		//log.info("<== Ended TemplateController.getAllActive() ==>");
		return response;
	}

	
	@DeleteMapping("/softdelete/{id}")
	public BaseDTO softDelete(@PathVariable("id") UUID id) {
		//log.info("<--- Started TemplateController  softDelete()  STARTED --->");
		BaseDTO baseDTO = templateService.softDelete(id);
		//log.info("<---  TemplateController  softDelete()  END --->");
		return baseDTO;
	}

}
