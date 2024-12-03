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
import com.oasys.uppcl_user__master_management.dto.StoreCategoryDTO;
import com.oasys.uppcl_user__master_management.service.StoreCategoryService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/storecategory")
@Log4j2
public class StoreCategoryController {
	
	@Autowired
	StoreCategoryService storeCategoryService;

	
	@PostMapping("/create")
	public BaseDTO createStoreCategory(@Valid @RequestBody StoreCategoryDTO storeCategoryDTO) {
		//log.info("<--- StoreCategory create Controller STARTED --->");
		BaseDTO baseDTO = storeCategoryService.create(storeCategoryDTO);
		//log.info("<--- StoreCategory create Controller END --->");
		return baseDTO;
	}

	
	@GetMapping("/getall")
	public BaseDTO getAll() {
		//log.info("<--- StoreCategory getAll Controller STARTED --->");
		BaseDTO baseDTO = storeCategoryService.getAll();
		//log.info("<--- StoreCategory getAll Controller END --->");
		return baseDTO;
	}

	
	@GetMapping("/getallactive")
	public BaseDTO getAllActive() {
		//log.info("<--- StoreCategory getAll Controller STARTED --->");
		BaseDTO baseDTO = storeCategoryService.getAllActive();
		//log.info("<--- StoreCategory getAll Controller END --->");
		return baseDTO;
	}

	
	
	
	@PutMapping("/update/{id}")
	public BaseDTO updateStoreCategory(@PathVariable("id") UUID id,@Valid @RequestBody StoreCategoryDTO storeCategoryDTO) {
		//log.info("<--- StoreCategory update Controller STARTED --->");
		BaseDTO baseDTO = storeCategoryService.update(id, storeCategoryDTO);
		//log.info("<--- StoreCategory update Controller END --->");
		return baseDTO;
	}
    
	
	@PostMapping("/lazylist")
	public BaseDTO getLazyList(@RequestBody PaginationRequestDTO requestData) {
		BaseDTO baseDTO = new BaseDTO();
		//log.info("<--- StoreCategory lazylist Controller STARTED --->");
		baseDTO = storeCategoryService.getLazyList(requestData);
		//log.info("<--- StoreCategory lazylist Controller END --->");
		return baseDTO;
	}
	
	
	@GetMapping("/get/{id}")
	public BaseDTO getById(@PathVariable("id") UUID id) {
		//log.info("<--- StoreCategory getById Controller STARTED --->");
		BaseDTO baseDTO = storeCategoryService.getById(id);
		//log.info("<--- StoreCategory getById Controller END --->");
		return baseDTO;
	}
	
	
	@DeleteMapping("/delete/{id}")
	public BaseDTO deleteStoreCategory(@PathVariable("id") UUID id) {
		//log.info("<--- StoreCategory delete Controller STARTED --->");
		BaseDTO baseDTO = storeCategoryService.delete(id);
		//log.info("<--- StoreCategory delete Controller END --->");
		return baseDTO;
	}
	
	
	@DeleteMapping("/softdelete/{id}")
	public BaseDTO softDelete(@PathVariable("id") UUID id) {
		//log.info("<--- StoreCategory delete Controller STARTED --->");
		BaseDTO baseDTO = storeCategoryService.softDelete(id);
		//log.info("<--- StoreCategory delete Controller ENDED --->");
		return baseDTO;
	}

}
