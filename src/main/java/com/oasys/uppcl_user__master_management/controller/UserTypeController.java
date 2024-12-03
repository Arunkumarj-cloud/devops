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
import com.oasys.uppcl_user__master_management.dto.UserTypeDTO;
import com.oasys.uppcl_user__master_management.service.UserTypeService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/usertype")
@Log4j2
public class UserTypeController {
	
	@Autowired
	UserTypeService userTypeService;

	
	@PostMapping("/create")
	public BaseDTO create(@Valid @RequestBody UserTypeDTO userTypeDTO) {
		//log.info("<--- UserType create Controller STARTED --->");
		BaseDTO response = userTypeService.create(userTypeDTO);
		//log.info("<--- UserType create Controller END --->");
		return response;
	}


	
	@PutMapping("/update/{id}")
	public BaseDTO update(@PathVariable("id") UUID id,@Valid @RequestBody UserTypeDTO userTypeDTO) {
		//log.info("<--- UserType update Controller STARTED --->");
		BaseDTO response = userTypeService.update(id,userTypeDTO);
		//log.info("<--- UserType update Controller END --->");
		return response;
	}

	
	@GetMapping("/get/{id}")
	public BaseDTO getById(@PathVariable("id") UUID id) {
		//log.info("<--- UserType getById Controller STARTED --->");
		BaseDTO response = userTypeService.getById(id);
		//log.info("<--- UserType getById Controller END --->");
		return response;
	}
	
	
	@GetMapping("/getall")
	public BaseDTO getAll() {
		//log.info("<--- UserType getAll Controller STARTED --->");
		BaseDTO response = userTypeService.getAll();
		//log.info("<--- UserType getAll Controller END --->");
		return response;
	}
	
	
	@GetMapping("/getallactive")
	public BaseDTO getAllActive() {
		//log.info("<--- UserType getAll Controller STARTED --->");
		BaseDTO response = userTypeService.getAllActive();
		//log.info("<--- UserType getAll Controller END --->");
		return response;
	}
	
	
	@PostMapping("/lazylist")
	public BaseDTO getLazyList(@RequestBody PaginationRequestDTO requestData) {
		//log.info("<--- UserType lazylist Controller STARTED --->");
		BaseDTO response = userTypeService.getLazyList(requestData);
		//log.info("<--- UserType lazylist Controller END --->");
		return response;
	}

	
	@DeleteMapping("/delete/{id}")
	public BaseDTO delete(@PathVariable("id") UUID id) {
		//log.info("<--- UserType delete Controller STARTED --->");
		BaseDTO response = userTypeService.delete(id);
		//log.info("<--- UserType delete Controller END --->");
		return response;
	}
	
	@PutMapping("/softdelete/{id}")
	public BaseDTO softDelete(@PathVariable("id") UUID id) {
		//log.info("<--- UserType softDelete Controller STARTED --->");
		BaseDTO response = userTypeService.softDelete(id);
		//log.info("<--- UserType softDelete Controller END --->");
		return response;
	}
	
	
	
	@GetMapping("/getbyname/{name}")
	public BaseDTO getByName(@PathVariable("name") String name) {
		//log.info("<--- UserType getByName Controller STARTED --->");
		BaseDTO response = userTypeService.getByName(name);
		//log.info("<--- UserType getByName Controller END --->");
		return response;
	}
	
	
	@GetMapping("/validateusertype/{name}")
	public BaseDTO validateUserType(@PathVariable String name) {
		//log.info("<--- UserType validateUserType Controller STARTED --->");
		BaseDTO response = userTypeService.validateUserType(name);
		//log.info("<--- UserType validateUserType Controller END --->");
		return response;
	}

}
