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
import com.oasys.uppcl_user__master_management.dto.BlacklistedPhoneDTO;
import com.oasys.uppcl_user__master_management.service.BlacklistedPhoneNumberService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/blacklisted-numbers")
public class BlacklistedPhoneNumberController {

	@Autowired
	private BlacklistedPhoneNumberService blacklistedPhoneNumberService;

	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@PostMapping("/upload")
	@ApiOperation(value = "This api is used upload fake phone numbers", notes = "Returns HTTP 200 if successful get the record")
	public FileUploadResponseDTO upload(@RequestParam(value = "file", required = true) MultipartFile file) {
		return blacklistedPhoneNumberService.upload(file);
	}

	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@PostMapping("/validate")
	@ApiOperation(value = "This api is used to check if phone number is blacklisted or not", notes = "Returns HTTP 200 if successful get the record")
	public BaseDTO upload(@RequestParam(value = "phoneNumber", required = true) String phoneNumber) {
		return blacklistedPhoneNumberService.validate(phoneNumber);
	}

	//@PreAuthorize(PrivilegeConstant.ADD_BLACKLIST_PHONE)
	@PostMapping("/create")
	@ApiOperation(value = "This api is used to Add blacklist phoneNumber", notes = "Returns HTTP 200 if successful get the record")
	public BaseDTO create(@Valid @RequestBody BlacklistedPhoneDTO blacklistedPhoneDTO) {

		BaseDTO response = blacklistedPhoneNumberService.create(blacklistedPhoneDTO);

		return response;
	}

	//@PreAuthorize(PrivilegeConstant.UPDATE_BLACKLIST_PHONE)
	@PutMapping("/update/{id}")
	@ApiOperation(value = "This api is used to update blacklist phoneNumber", notes = "Returns HTTP 200 if successful get the record")
	public BaseDTO update(@PathVariable("id") UUID id,
			@RequestParam(value = "phoneNumber", required = true) String phoneNumber) {

		BaseDTO response = blacklistedPhoneNumberService.update(id, phoneNumber);

		return response;
	}

	//@PreAuthorize(PrivilegeConstant.VIEW_BLACKLIST_PHONE)
	@GetMapping("/get/{id}")
	@ApiOperation(value = "This api is used to get by id blacklist phoneNumber", notes = "Returns HTTP 200 if successful get the record")
	public BaseDTO get(@PathVariable("id") UUID id) {

		BaseDTO response = blacklistedPhoneNumberService.getById(id);

		return response;
	}

	//@PreAuthorize(PrivilegeConstant.VIEW_LIST_BLACKLIST_PHONE)
	@GetMapping("/getAll")
	@ApiOperation(value = "This api is used to get all blacklist phoneNumber", notes = "Returns HTTP 200 if successful get the record")
	public BaseDTO getAll() {

		BaseDTO response = blacklistedPhoneNumberService.getAll();

		return response;
	}

	//@PreAuthorize(PrivilegeConstant.VIEW_LIST_BLACKLIST_PHONE)
	@ApiOperation(value = "This api is used to get  the list of  blacklist phoneNumber", notes = "Returns HTTP 200 if successful get the record")
	@PostMapping("/lazylist")
	public BaseDTO getLazyList(@RequestBody PaginationRequestDTO requestData) {

		BaseDTO response = blacklistedPhoneNumberService.getLazyList(requestData);

		return response;
	}
}
