package com.oasys.uppcl_user__master_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oasys.config.BaseDTO;
import com.oasys.uppcl_user__master_management.dto.IfscDTO;
import com.oasys.uppcl_user__master_management.service.IfscCodeService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping("/data")
public class IfscCodeController {
	@Autowired
	IfscCodeService ifsccodeService;
	
	//@PreAuthorize("#oauth2.hasScope('Admin') or #oauth2.hasScope('Sales Officer')  ")
	@ApiOperation(value = "Ifsc Data", response = BaseDTO.class)
	@PostMapping("/ifscCodedata")
	public BaseDTO ifsccodeBankDetails(@RequestBody IfscDTO walletDto) {
		//log.info("<====Starts IfscController.getifscBankDetails()=====>");
		BaseDTO responseDTO = new BaseDTO();
		responseDTO = ifsccodeService.ifsccodedata(walletDto);
		//log.info("<====End IfscController.getifscBankDetails()=====>");
		return responseDTO;
	}

}
