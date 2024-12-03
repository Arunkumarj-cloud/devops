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
import com.oasys.uppcl_user__master_management.dto.NatureOfBusinessMasterDto;
import com.oasys.uppcl_user__master_management.dto.NatureOfBusinessUpdateDTO;
import com.oasys.uppcl_user__master_management.service.NatureOfBusinessService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/nature")
@Log4j2

public class NatureOfBusinessController {

	
	@Autowired
	NatureOfBusinessService nobms;
	
//	@PreAuthorize(PrivilegeConstant.ADD_NATURE_OF_BUSINESS)
	@PostMapping("/create")
	public BaseDTO createNatureOfBusiness(@Valid @RequestBody NatureOfBusinessMasterDto  natureOfBusinessDTO) {
		//log.info(" <====Started NatureOfBusinessController.createNatureOfBusiness=====>");
		BaseDTO response=new BaseDTO();
		response = nobms.createNatureOfBusiness(natureOfBusinessDTO);
		//log.info(" <====Ended NatureOfBusinessController.createNatureOfBusiness=====>");
		return response;
		
	}
//	@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/get")
	public BaseDTO getNatureOfBusiness() {
		//log.info("<===Started NatureOfBusinessController.getNatureOfBusiness ===>");
		BaseDTO response= nobms.getNatureOfBusiness();
		//log.info("<===Ended NatureOfBusinessController.getNatureOfBusiness ===>");
		return response;	
		}
	//@PreAuthorize(PrivilegeConstant.UPDATE_NATURE_OF_BUSINESS)
	@PutMapping("/update/{id}")
	public BaseDTO updateNatureOfBusiness(@PathVariable UUID id, @Valid @RequestBody NatureOfBusinessUpdateDTO natureOfBusinessDTO) {
		//log.info("<===Started NatureOfBusinessController.updateNatureOfBusiness ===>");
		BaseDTO response= nobms.updateNatureOfBusiness(id,natureOfBusinessDTO);
		//log.info("<===Ended NatureOfBusinessController.updateNatureOfBusiness ===>");
		return response;	
		}

	//@PreAuthorize("#oauth2.hasScope('Admin')")
//	@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@PostMapping("/lazylist")
	public BaseDTO getAllNatureOfBusinesslazy(@RequestBody PaginationRequestDTO pageData) {
		//log.info("<== Started NatureOfBusinessController.getAllMaritalStatuslazy() ==>");
		BaseDTO response = nobms.getAllNatureOfBusinesslazy(pageData);
		//log.info("<== Ended NatureOfBusinessController.getAllNatureOfBusinesslazy()  ==>");
		return response;
	}
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/getallactive")
	public BaseDTO getAllActive() {
		//log.info("<== Started NatureOfBusinessController.getAllActive() ==>");
		BaseDTO response = nobms.getAllActive();
		//log.info("<== Ended NatureOfBusinessController.getAllActive()  ==>");
		return response;
	}
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@DeleteMapping("/delete/{id}")
	public BaseDTO deleteNature(@PathVariable UUID id) {
		//log.info("<===Started NatureOfBusinessController.deleteNature ===>");
		BaseDTO response= nobms.deleteNature(id);
		//log.info("<===Ended NatureOfBusinessController.deleteNature ===>");
		return response;	
		}
//	@PreAuthorize("#oauth2.hasScope('Admin')")
	@PutMapping("/softdelete/{id}")
	public BaseDTO softDelete(@PathVariable("id") UUID id) {
		//log.info("<--- Started NatureOfBusinessController  softDelete  STARTED --->");
		BaseDTO baseDTO =nobms .softDelete(id);
		//log.info("<---  NatureOfBusinessController  softDelete  END --->");
		return baseDTO;
	}
	@GetMapping("/get/{id}")
	public BaseDTO getNatureOfBusinessById(@PathVariable UUID id) {
		//log.info("<===Started NatureOfBusinessController.getNatureOfBusinessById ===>");
		BaseDTO response= nobms.getNatureOfBusinessById(id);
		//log.info("<===Ended NatureOfBusinessController.getNatureOfBusinessById ===>");
		return response;	
		}

}
