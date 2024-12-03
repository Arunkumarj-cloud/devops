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
import com.oasys.uppcl_user__master_management.dto.MuncipalityDTO;
import com.oasys.uppcl_user__master_management.service.MuncipalityService;

import lombok.extern.log4j.Log4j2;
@RestController
@RequestMapping("/muncipality")
@Log4j2

public class MuncipalityController {
	@Autowired(required = false)
	MuncipalityService  muncipalityService;
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@PostMapping("/create")
	public BaseDTO createMuncipality(@Valid @RequestBody MuncipalityDTO  muncipalityDTO) {
		//log.info(" <====Started MuncipalityController.createMuncipality=====>");
		BaseDTO response=new BaseDTO();
		response = muncipalityService.createMuncipality(muncipalityDTO);
		//log.info(" <====Ended MuncipalityController.createMuncipality=====>");
		return response;
		
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@GetMapping("/get")
	public BaseDTO getMuncipality() {
		//log.info("<===Started MuncipalityController.getMuncipality ===>");
		BaseDTO response= muncipalityService.getMuncipality();
		//log.info("<===Ended MuncipalityController.getMuncipality ===>");
		return response;	
		}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@GetMapping("/get/{id}")
	public BaseDTO getMuncipalityById(@PathVariable UUID id) {
		//log.info("<===Started MuncipalityController.getMuncipalityById ===>");
		BaseDTO response= muncipalityService.getMuncipalityById(id);
		//log.info("<===Ended MuncipalityController.getMuncipalityById ===>");
		return response;	
		}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@PutMapping("/update/{id}")
	public BaseDTO updateMuncipality(@PathVariable UUID id, @Valid @RequestBody MuncipalityDTO muncipalityDTO) {
		//log.info("<===Started MuncipalityController.updateMuncipality ===>");
		BaseDTO response= muncipalityService.updateMuncipality(id,muncipalityDTO);
		//log.info("<===Ended MuncipalityController.updateMuncipality ===>");
		return response;	
		}

	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@PutMapping("/softdelete/{id}")
	public BaseDTO softDelete(@PathVariable UUID id) {
		//log.info("<===Started MuncipalityController.softDelete ===>");
		BaseDTO response= muncipalityService.softDelete(id);
		//log.info("<===Ended MuncipalityController.softDelete ===>");
		return response;	
		}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@PostMapping("/lazylist")
	public BaseDTO getLazyList(@RequestBody PaginationRequestDTO dto) {
		//log.info(" <====Started MuncipalityController.getLazyList()=====>");
		BaseDTO response=new BaseDTO();
		response = muncipalityService.getLazyList(dto);
		//log.info(" <====Ended MuncipalityController.getLazyList()=====>");
		return response;
		
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@GetMapping("/getDistrictById/{id}")
	public BaseDTO getDistrictById(@PathVariable UUID id) {
		//log.info("<===Started MuncipalityController.getDistrictById ===>");
		BaseDTO response= muncipalityService.getDistrictById(id);
		//log.info("<===Ended MuncipalityController.getDistrictById ===>");
		return response;	
		}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@DeleteMapping("/delete/{id}")
	public BaseDTO deleteMunicipality(@PathVariable("id") UUID id) {
		//log.info("<--- MuncipalityController delete Controller STARTED --->");
		BaseDTO response = muncipalityService.deleteMunicipality(id);
		//log.info("<--- MuncipalityController delete Controller END --->");
		return response;
	}

}
