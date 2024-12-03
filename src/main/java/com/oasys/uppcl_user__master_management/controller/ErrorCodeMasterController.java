package com.oasys.uppcl_user__master_management.controller;

import java.util.List;
import java.util.UUID;

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
import org.springframework.web.multipart.MultipartFile;

import com.oasys.config.BaseDTO;
import com.oasys.uppcl_user__master_management.dto.errorCodeDTO;
import com.oasys.uppcl_user__master_management.service.impl.ErrorCodeMasterServiceImp;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/errorCode")
@Log4j2
public class ErrorCodeMasterController {
	
	@Autowired
	ErrorCodeMasterServiceImp errorCodeMasterService;
	
	@PostMapping("/addmultiplebank")
	public BaseDTO addpost(@RequestBody List<errorCodeDTO> bankDTO) {
		log.info("<==== ErrorCodeMasterController.AddMultipleBank() ===> Starts");
		BaseDTO responseDTO = errorCodeMasterService.addmultiplebank(bankDTO);
		return responseDTO;
	}
	
	@PostMapping("/addbank")
	public BaseDTO addBank(@RequestBody errorCodeDTO bankDto) {
		log.info("<==== ErrorCodeMasterController.AddBank() ===> Starts");
		BaseDTO responseDto = errorCodeMasterService.addBank(bankDto);
		return responseDto;
	}
	
	@GetMapping("/getall")
	public BaseDTO getAll() {
		log.info("<==== ErrorCodeMasterController.GetAll() ===> Starts");
		BaseDTO responseDto=errorCodeMasterService.getAll();
		return responseDto;
	}
	
	@GetMapping("/get/{id}")
	public BaseDTO getById(@PathVariable("id") UUID id) {
		log.info("<==== ErrorCodeMasterController.getById() ===> Starts");
		BaseDTO responseDto= errorCodeMasterService.getById(id);
		return responseDto;
	}
	
	@PutMapping("/update/{id}")
	public BaseDTO updateById(@PathVariable("id") UUID id, @RequestBody errorCodeDTO bankDto) {
		log.info("<==== ErrorCodeMasterController.updateById() ===> Starts");
		BaseDTO responseDto = errorCodeMasterService.updateById(id,bankDto);
		return responseDto;
	}
	
	@DeleteMapping("/softdelete/{id}")
	public BaseDTO softDelete(@PathVariable("id") UUID id) {
		BaseDTO responseDto = errorCodeMasterService.softDelete(id);
		return responseDto;
	}
	
	@PostMapping("/uploadexcel")
	public BaseDTO addExcel(@RequestParam("file") MultipartFile file) {
		BaseDTO responseDto=errorCodeMasterService.importExcelFile(file);
		return responseDto;
		
	}
	
		

}
