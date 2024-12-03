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
import com.oasys.uppcl_user__master_management.dto.CityMasterDTO;
import com.oasys.uppcl_user__master_management.service.CityMasterService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/city")
@Log4j2
public class CityMasterController {

	@Autowired
	CityMasterService cityMasterService;
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@PostMapping("/create")
	public BaseDTO Create(@Valid @RequestBody CityMasterDTO cityMasterDTO) {
		//log.info("<--- Started CityMasterController.create --->");
		BaseDTO baseDTO = cityMasterService.create(cityMasterDTO);
		//log.info("<--- Ended CityMasterController.create --->");
		return baseDTO;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/getAll")
	public BaseDTO getAll() {
		//log.info("<--- Started CityMasterController.getAll --->");
		BaseDTO baseDTO = cityMasterService.getAll();
		//log.info("<--- Ended CityMasterController.getAll --->");
		return baseDTO;
		
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@PutMapping("/update/{id}")
	public BaseDTO update(@PathVariable("id") UUID id,@Valid @RequestBody CityMasterDTO cityMasterDTO) {
		//log.info("<--- Started CityMasterController.update --->");
		BaseDTO baseDTO = cityMasterService.update(id,cityMasterDTO);
		//log.info("<--- Ended CityMasterController.update --->");
		return baseDTO;
	}
}
