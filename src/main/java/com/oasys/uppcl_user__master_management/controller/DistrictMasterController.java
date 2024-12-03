package com.oasys.uppcl_user__master_management.controller;

import java.util.Set;
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
import com.oasys.uppcl_user__master_management.dto.DistrictMasterDTO;
import com.oasys.uppcl_user__master_management.service.DistrictMasterService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/district")
@Log4j2
public class DistrictMasterController {
		

	@Autowired
	DistrictMasterService districtService;
	
	
	//@PreAuthorize(PrivilegeConstant.ADD_DISTRICT)
    @PostMapping("/create")
	public BaseDTO create(@Valid @RequestBody DistrictMasterDTO districtMasterDTO) {
		//log.info("<--- Started DistrictMasterController.create --->");
		BaseDTO baseDTO = districtService.create(districtMasterDTO);
		//log.info("<--- Ended DistrictMasterController.create --->");
		return baseDTO;
	}
	
	
	@GetMapping("/getbyname/{name}")
	public BaseDTO getByName(@PathVariable("name") String name) {	
		BaseDTO response = districtService.getByName(name);
		return response;
	}
    

	@GetMapping("/getAll")
	public BaseDTO getAll() {
		//log.info("<--- Started DistrictMasterController.getAll --->");
		BaseDTO baseDTO = districtService.getAll();
		//log.info("<--- Ended DistrictMasterController.getAll --->");
		return baseDTO;
	}

	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/getallactive")
	public BaseDTO getAllActive() {
		//log.info("<--- DistrictName getAll Controller STARTED --->");
		BaseDTO response = districtService.getAllActive();
		//log.info("<--- DistrictName getAll Controller END --->");
		return response;
	}

	//@PreAuthorize(PrivilegeConstant.UPDATE_DISTRICT)
	@PutMapping("/update/{id}")
	public BaseDTO update(@PathVariable("id") UUID id, @Valid @RequestBody DistrictMasterDTO districtMasterDTO) {
		//log.info("<--- Started DistrictMasterController.update --->");
		BaseDTO baseDTO = districtService.update(id,districtMasterDTO);
		//log.info("<--- Ended DistrictMasterController.update --->");
		return baseDTO;
	}
    
	//@PreAuthorize("#oauth2.hasScope('Admin') or #oauth2.hasScope('Sales Officer') or #oauth2.hasScope('Customer Support Officer') or #oauth2.hasScope('Finance Officer')  or #oauth2.hasScope('Merchant') or #oauth2.hasScope('TenantAdmin') or #oauth2.hasScope('Third Party') or #oauth2.hasScope('MIS')")
	
	@GetMapping("/get/{id}")
	public BaseDTO getById(@PathVariable("id") UUID id) {
		//log.info("<--- Started DistrictMasterController.update --->");
		BaseDTO baseDTO = districtService.getById(id);
		//log.info("<--- Ended DistrictMasterController.update --->");
		return baseDTO;
	}
	
	//@PreAuthorize("isAuthenticated()")
	@GetMapping("/getby/{ids}")
	public BaseDTO getByIds(@PathVariable("ids") Set<UUID> ids) {
		BaseDTO baseDTO = districtService.getByIds(ids);
		return baseDTO;
	}
     
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@DeleteMapping("/delete/{id}")
	public BaseDTO delete(@PathVariable("id") UUID id) {
		//log.info("<--- Started DistrictMasterController.delete --->");
		BaseDTO baseDTO = districtService.delete(id);
		//log.info("<--- Ended DistrictMasterController.delete --->");
		return baseDTO;
	}
	
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@PostMapping("/lazylist")
	public BaseDTO getLazyList(@RequestBody PaginationRequestDTO requestData) {
		BaseDTO baseDTO = new BaseDTO();
		//log.info("<--- DistrictName getById Controller STARTED --->");
		baseDTO = districtService.getLazyList(requestData);
		//log.info("<--- DistrictName getById Controller END --->");
		return baseDTO;
	}

	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@PutMapping("/softdelete/{id}")
	public BaseDTO softDelete(@PathVariable("id") UUID id) {
		//log.info("<--- DistrictName delete Controller STARTED --->");
		BaseDTO baseDTO = districtService.softDelete(id);
		//log.info("<--- DistrictName delete Controller END --->");
		return baseDTO;
	}
	
	

	//@PreAuthorize("isAuthenticated()")
	//@PreAuthorize("#oauth2.hasScope('MIS')")
	//@PreAuthorize("#oauth2.hasScope('Admin') or #oauth2.hasScope('Sales Officer') or #oauth2.hasScope('Customer Support Officer') or #oauth2.hasScope('Finance Officer') or #oauth2.hasScope('Merchant') or #oauth2.hasScope('TenantAdmin') or #oauth2.hasScope('Third Party') or #oauth2.hasScope('MIS')")
	@GetMapping("/getByStateId/{id}")
	public BaseDTO getByStateId(@PathVariable("id") UUID id) {
		//log.info("<--- Started DistrictMasterController.GetByStateID --->");
		BaseDTO baseDTO = districtService.getByStateId(id);
		//log.info("<--- Ended DistrictMasterController.GetBySntateID --->");
		return baseDTO;
	}
	
	
	//@PreAuthorize("#oauth2.hasScope('Admin') or #oauth2.hasScope('Third Party') ")
	@GetMapping("/getBydistricName/{districName}/{stateId}")
	public BaseDTO getBydistricName(@PathVariable("districName") String districName,@PathVariable("stateId") UUID stateId) {
		//log.info("<--- Started DistrictMasterController.getBydistricName --->");
		BaseDTO baseDTO = districtService.getBydistricName(districName,stateId);
		//log.info("<--- Ended DistrictMasterController.getBydistricName --->" +districName + stateId);
		return baseDTO;
	}


}
