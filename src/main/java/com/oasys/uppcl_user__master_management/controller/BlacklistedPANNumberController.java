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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.oasys.config.BaseDTO;
import com.oasys.config.FileUploadResponseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.BlacklistedPanDTO;
import com.oasys.uppcl_user__master_management.service.BlacklistedPANNumberService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/blacklisted-pan-numbers")
public class BlacklistedPANNumberController {

	@Autowired
	private BlacklistedPANNumberService blacklistedPANNumberService;

	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@PostMapping("/upload")
	@ApiOperation(value = "This api is used upload fake PAN numbers", notes = "Returns HTTP 200 if successful get the record")
	public FileUploadResponseDTO upload(@RequestParam(value = "file", required = true) MultipartFile file) {
		return blacklistedPANNumberService.upload(file);
	}

	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@PostMapping("/validate")
	@ApiOperation(value = "This api is used to check if pan number is blacklisted or not", notes = "Returns HTTP 200 if successful get the record")
	public BaseDTO upload(@RequestParam(value = "panNumber", required = true) String panNumber) {
		return blacklistedPANNumberService.validate(panNumber);
	}

	//@PreAuthorize(PrivilegeConstant.ADD_BLACKLIST_PAN)
	@PostMapping("/create")
	@ApiOperation(value = "This api is used to Add blacklist pan", notes = "Returns HTTP 200 if successful get the record")
	public BaseDTO create(@Valid @RequestBody BlacklistedPanDTO blacklistedPanDTO) {
		BaseDTO response = blacklistedPANNumberService.create(blacklistedPanDTO);
		return response;
	}

	//@PreAuthorize(PrivilegeConstant.UPDATE_BLACKLIST_PAN)
	@PutMapping("/update/{id}")
	@ApiOperation(value = "This api is used to update blacklist pan", notes = "Returns HTTP 200 if successful get the record")
	public BaseDTO update(@PathVariable("id") UUID id,
			@RequestParam(value = "panNumber", required = true) String panNumber) {
		BaseDTO response = blacklistedPANNumberService.update(id, panNumber);
		return response;
	}

	//@PreAuthorize(PrivilegeConstant.VIEW_BLACKLIST_PAN)
	@GetMapping("/get/{id}")
	@ApiOperation(value = "This api is used to get by id blacklist pan", notes = "Returns HTTP 200 if successful get the record")
	public BaseDTO get(@PathVariable("id") UUID id) {
		BaseDTO response = blacklistedPANNumberService.getById(id);
		return response;
	}

	//@PreAuthorize(PrivilegeConstant.VIEW_LIST_BLACKLIST_PAN)
	@GetMapping("/getAll")
	@ApiOperation(value = "This api is used to get all blacklist pan", notes = "Returns HTTP 200 if successful get the record")
	public BaseDTO getAll() {
		BaseDTO response = blacklistedPANNumberService.getAll();
		return response;
	}

	//@PreAuthorize(PrivilegeConstant.VIEW_LIST_BLACKLIST_PAN)
	@ApiOperation(value = "This api is used to get  the list of  blacklist pan", notes = "Returns HTTP 200 if successful get the record")
	@PostMapping("/lazylist")
	public BaseDTO getLazyList(@RequestBody PaginationRequestDTO requestData) {
		BaseDTO response = blacklistedPANNumberService.getLazyList(requestData);
		return response;
	}

}
