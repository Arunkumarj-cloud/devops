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
import com.oasys.uppcl_user__master_management.dto.TypeOfAccountDTO;
import com.oasys.uppcl_user__master_management.service.TypeOfAccountService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/typeofaccount")
@Log4j2
public class TypeOfAccountController {
	
	@Autowired
	TypeOfAccountService typeOfAccountService;
  
	
	@PostMapping("/create")
	public BaseDTO create(@Valid @RequestBody TypeOfAccountDTO typeOfAccountDTO) {
		//log.info("<--- TypeOfAccount create Controller STARTED --->");
		BaseDTO response = typeOfAccountService.create(typeOfAccountDTO);
		//log.info("<--- TypeOfAccount create Controller END --->");
		return response;
	}

	
	@PutMapping("/update/{id}")
	public BaseDTO update(@PathVariable("id") UUID id,@Valid @RequestBody TypeOfAccountDTO typeOfAccountDTO) {
		//log.info("<--- TypeOfAccount update Controller STARTED --->");
		BaseDTO response = typeOfAccountService.update(id,typeOfAccountDTO);
		//log.info("<--- TypeOfAccount update Controller END --->");
		return response;
	}

	
	@GetMapping("/get/{id}")
	public BaseDTO getById(@PathVariable("id") UUID id) {
		//log.info("<--- TypeOfAccount getById Controller STARTED --->");
		BaseDTO response = typeOfAccountService.getById(id);
		//log.info("<--- TypeOfAccount getById Controller END --->");
		return response;
	}
	
	
	
	@GetMapping("/getall")
	public BaseDTO getAll() {
		//log.info("<--- TypeOfAccount getAll Controller STARTED --->");
		BaseDTO response = typeOfAccountService.getAll();
		//log.info("<--- TypeOfAccount getAll Controller END --->");
		return response;
	}
	
	
	@GetMapping("/getallactive")
	public BaseDTO getAllActive() {
		//log.info("<--- TypeOfAccount getAll Controller STARTED --->");
		BaseDTO response = typeOfAccountService.getAllActive();
		//log.info("<--- TypeOfAccount getAll Controller END --->");
		return response;
	}
	
	
	@PostMapping("/lazylist")
	public BaseDTO getLazyList(@RequestBody PaginationRequestDTO requestData) {
		//log.info("<--- TypeOfAccount lazylist Controller STARTED --->");
		BaseDTO response = typeOfAccountService.getLazyList(requestData);
		//log.info("<--- TypeOfAccount lazylist Controller END --->");
		return response;
	}

	
	@DeleteMapping("/delete/{id}")
	public BaseDTO delete(@PathVariable("id") UUID id) {
		//log.info("<--- TypeOfAccount delete Controller STARTED --->");
		BaseDTO response = typeOfAccountService.delete(id);
		//log.info("<--- TypeOfAccount delete Controller END --->");
		return response;
	}
	
	
	@PutMapping("/softdelete/{id}")
	public BaseDTO softDelete(@PathVariable("id") UUID id) {
		//log.info("<--- TypeOfAccount softDelete Controller STARTED --->");
		BaseDTO response = typeOfAccountService.softDelete(id);
		//log.info("<--- TypeOfAccount softDelete Controller END --->");
		return response;
	}

}
