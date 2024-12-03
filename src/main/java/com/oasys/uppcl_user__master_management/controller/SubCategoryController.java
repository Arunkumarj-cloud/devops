package com.oasys.uppcl_user__master_management.controller;

import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oasys.config.BaseDTO;

import com.oasys.uppcl_user__master_management.dto.SearchDTO;
import com.oasys.uppcl_user__master_management.dto.SubCategoryDTO;
import com.oasys.uppcl_user__master_management.service.SubCategoryService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/subCategory")
@Log4j2
public class SubCategoryController {

	@Autowired
	SubCategoryService subCategoryService;

	
	@PostMapping("/create")
	public BaseDTO create(@Valid @RequestBody SubCategoryDTO dto) {
		// log.info("<--- Started SubCategoryController.create --->");
		BaseDTO baseDTO = subCategoryService.create(dto);
		// log.info("<--- Ended SubCategoryController.create --->");
		return baseDTO;
	}

	
	@GetMapping("/getallactive")
	public BaseDTO getAllActive() {
		// log.info("<--- SubCategoryController getAll Controller STARTED --->");
		BaseDTO response = subCategoryService.getAllActive();
		// log.info("<--- SubCategoryController getAll Controller END --->");
		return response;
	}

	
	@PutMapping("/update/{id}")
	public BaseDTO update(@PathVariable("id") UUID id, @Valid @RequestBody SubCategoryDTO dto) {
		// log.info("<--- Started SubCategoryController.update --->");
		BaseDTO baseDTO = subCategoryService.update(id, dto);
		// log.info("<--- Ended SubCategoryController.update --->");
		return baseDTO;
	}

	
	@DeleteMapping("/delete/{id}")
	public BaseDTO delete(@PathVariable("id") UUID id) {
		// log.info("<--- Started SubCategoryController.delete --->");
		BaseDTO baseDTO = subCategoryService.delete(id);
		// log.info("<--- Ended SubCategoryController.delete --->");
		return baseDTO;
	}

	
	@GetMapping("/getByServiceCategoryId/{id}")
	public BaseDTO getByServiceCategoryId(@PathVariable("id") UUID id) {
		// log.info("<--- Started SubCategoryController.getByServiceCategoryId --->");
		BaseDTO baseDTO = subCategoryService.getByServiceCategoryId(id);
		// log.info("<--- Ended SubCategoryController.getByServiceCategoryId --->");
		return baseDTO;
	}

	
	@GetMapping("/get/{id}")
	public BaseDTO getById(@PathVariable("id") UUID id) {
		// log.info("<--- ServiceProviderController getById STARTED --->");
		BaseDTO response = subCategoryService.getById(id);
		// log.info("<--- ServiceProviderController getById END --->");
		return response;
	}

	
	@PostMapping("/getallactivewithsearch")
	public BaseDTO getAllActiveWithSearch(@RequestBody SearchDTO searchDTO) {
		// log.info("<--- SubCategoryController getAll Controller STARTED --->");
		BaseDTO response = subCategoryService.getAllActiveWithSearch(searchDTO);
		// log.info("<--- SubCategoryController getAll Controller END --->");
		return response;
	}

	@GetMapping("/getByServiceCategoryByName/{name}")
	public BaseDTO getByServiceCategoryByName(@PathVariable("name") String name) {
		// log.info("<--- Started SubCategoryController.getByServiceCategoryId --->");
		BaseDTO baseDTO = subCategoryService.getByServiceCategoryByName(name);
		// log.info("<--- Ended SubCategoryController.getByServiceCategoryId --->");
		return baseDTO;
	}
	
	
	@GetMapping("/getByIds")
	public BaseDTO getAllByIds(@RequestParam(name="ids",required = true) List<UUID> ids) 
	{ 
		BaseDTO response = subCategoryService.getByIds(ids);
		return response;

	}
	@GetMapping("/getByName/{name}")
	public BaseDTO getByName(@PathVariable("name") String name) {
		// log.info("<--- Started SubCategoryController.getByServiceCategoryId --->");
		BaseDTO baseDTO = subCategoryService.getByName(name);
		// log.info("<--- Ended SubCategoryController.getByServiceCategoryId --->");
		return baseDTO;

	}
	
	@GetMapping("/getByName/{serviceName}/{subcategoryName}")
	public BaseDTO getByServiceAndSubcategory(@PathVariable("serviceName") String serviceName,@PathVariable("subcategoryName") String subcategoryName) {
		// log.info("<--- Started SubCategoryController.getByServiceCategoryId --->");
		BaseDTO baseDTO = subCategoryService.getByServiceAndSubcategory(serviceName,subcategoryName);
		// log.info("<--- Ended SubCategoryController.getByServiceCategoryId --->");
		return baseDTO;

	}
	
	@GetMapping("/all")
	public BaseDTO getAllOrderByModifiedDate() {	
		BaseDTO response =subCategoryService.getAllOrderByModifiedDate();
		return response;
	}
}
