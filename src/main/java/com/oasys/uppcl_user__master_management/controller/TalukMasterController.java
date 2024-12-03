package com.oasys.uppcl_user__master_management.controller;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.TalukMasterDTO;
import com.oasys.uppcl_user__master_management.service.TalukMasterService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/taluk")
@Log4j2
public class TalukMasterController {
	
	@Autowired
	TalukMasterService talukMasterService;

	
	@PostMapping("/create")
	public BaseDTO create(@Valid @RequestBody TalukMasterDTO talukMasterDTO) {
		//log.info("<--- TalukMaster create Controller STARTED --->");
		BaseDTO response = talukMasterService.create(talukMasterDTO);
		//log.info("<--- BankName create Controller END --->");
		return response;
	}
	
	@GetMapping("/getall")
	public BaseDTO getAll() {
		//log.info("<--- TalukMaster getAll Controller STARTED --->");
		BaseDTO response = talukMasterService.getAll();
		//log.info("<--- TalukMaster getAll Controller END --->");
		return response;
	}
	
	@PutMapping("/update/{id}")
	public BaseDTO update(@PathVariable("id") UUID id,@Valid @RequestBody TalukMasterDTO talukMasterDTO) {
		//log.info("<--- TalukMaster update Controller STARTED --->");
		BaseDTO response = talukMasterService.update(id,talukMasterDTO);
		//log.info("<--- TalukMaster update Controller END --->");
		return response;
	}
	
	@GetMapping("/get/{id}")
	public BaseDTO getById(@PathVariable("id") UUID id) {
		//log.info("<--- TalukMaster getById Controller STARTED --->");
		BaseDTO response = talukMasterService.getById(id);
		//log.info("<--- TalukMaster getById Controller END --->");
		return response;
	}
	
	@GetMapping("/getallactive")
	public BaseDTO getAllActive() {
		//log.info("<--- TalukMaster getAll Controller STARTED --->");
		BaseDTO response = talukMasterService.getAllActive();
		//log.info("<--- TalukMaster getAll Controller END --->");
		return response;
	}
	
	@PutMapping("/softdelete/{id}")
	public BaseDTO softDelete(@PathVariable("id") UUID id) {
		//log.info("<--- TalukMaster delete Controller STARTED --->");
		BaseDTO response = talukMasterService.softDelete(id);
		//log.info("<--- TalukMaster delete Controller END --->");
		return response;
	}
	
	@GetMapping("/getByDistrictId/{id}")
	public BaseDTO getByDistrictId(@PathVariable("id") UUID id) {
		//log.info("<--- Started TalukMaster.getByDistrictId --->");
		BaseDTO baseDTO = talukMasterService.getByDistrictId(id);
		//log.info("<--- Ended TalukMaster.getByDistrictId --->");
		return baseDTO;
	}
	
	@ApiOperation(value = "LazyList Service", response = BaseDTO.class)
	@PostMapping("/lazylist")
	public BaseDTO lazylistService(@Valid @RequestBody PaginationRequestDTO paginationRequestDTO) {
		BaseDTO responseDTO = talukMasterService.lazylist(paginationRequestDTO);
		return responseDTO;
	}

	
	

}
