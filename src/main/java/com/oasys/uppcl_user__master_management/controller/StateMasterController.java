package com.oasys.uppcl_user__master_management.controller;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.oasys.uppcl_user__master_management.dto.StateMasterDTO;
import com.oasys.uppcl_user__master_management.service.StateMasterService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/state")
@CrossOrigin(origins = { "*" })
public class StateMasterController {
	
	@Autowired
	private StateMasterService stateMasterService;

	
	//@PreAuthorize(PrivilegeConstant.ADD_STATE)
	@CrossOrigin(origins = { "*" })
	@PostMapping("/create")
	public BaseDTO saveState(@Valid @RequestBody StateMasterDTO dto) {
		//log.info("Entered in StateMasterController.saveState(StateMasterDTO dto)");
		BaseDTO response = stateMasterService.saveState(dto);
		//log.info("Finished Execution of StateMasterController.saveState(StateMasterDTO dto)");
		return response;
	}
	
	//@PreAuthorize(PrivilegeConstant.UPDATE_STATE)
	@PutMapping("/update/{id}")
	public BaseDTO updateState(@PathVariable("id") UUID id,@Valid @RequestBody StateMasterDTO dto) {
		//log.info("Entered in StateMasterController.updateState(StateMasterDTO dto)");
		BaseDTO response = stateMasterService.updateState(id,dto);
		//log.info("Finished Execution of StateMasterController.updateState(StateMasterDTO dto)");
		return response;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin') or #oauth2.hasScope('Sales Officer') or #oauth2.hasScope('Customer Support Officer') or #oauth2.hasScope('Finance Officer')  or #oauth2.hasScope('Merchant') or #oauth2.hasScope('TenantAdmin') or #oauth2.hasScope('Third Party') or #oauth2.hasScope('MIS')")
	@GetMapping("/get/{id}")
	public BaseDTO getState(@PathVariable("id") UUID id) {
		//log.info("Entered in StateMasterController.getState(UUID id)");
		BaseDTO response = stateMasterService.getState(id);
		//log.info("Finished Execution of StateMasterController.getState(UUID id)");
		return response;
	}
	
	
    
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@DeleteMapping("/delete")
	public BaseDTO deleteState(@RequestBody StateMasterDTO dto) {
		//log.info("Entered in StateMasterController.deleteState(StateMasterDTO dto)");
		BaseDTO response = stateMasterService.deleteState(dto);
		//log.info("Finished Execution of StateMasterController.deleteState(StateMasterDTO dto)");
		return response;
	}
	
	
	//@PreAuthorize("#oauth2.hasScope('Admin') or #oauth2.hasScope('Sales Officer') or #oauth2.hasScope('Customer Support Officer') or #oauth2.hasScope('Finance Officer')  or #oauth2.hasScope('Merchant') or #oauth2.hasScope('TenantAdmin') or #oauth2.hasScope('Third Party') or #oauth2.hasScope('MIS')")
	@GetMapping("/get")
	public BaseDTO getActiveStateList() {
		//log.info("Entered in StateMasterController.getActiveStateList()");
		BaseDTO response = stateMasterService.getActiveStateList();
		//log.info("Finished Execution of StateMasterController.getActiveStateList()");
		return response;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@CrossOrigin(origins = { "*" })
	@GetMapping("/getAll")
	public BaseDTO getAll() {
		BaseDTO response=stateMasterService.getAll();
		return response;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@DeleteMapping("/softdelete/{id}")
	public BaseDTO softDelete(@PathVariable("id") UUID id) {
		//log.info("Entered in StateMasterController.softDelete() STARTED --->");
		BaseDTO response = stateMasterService.softDelete(id);
		//log.info("Finished in StateMasterController.softDelete() ENDED --->");
		return response;
	}
	
	
	//@PreAuthorize("#oauth2.hasScope('Admin') or #oauth2.hasScope('Third Party') ")
    @GetMapping("/getstateName/{stateName}")
	public BaseDTO getStateName(@PathVariable("stateName") String stateName) {
		//log.info("Entered in StateMasterController.getstateName(String stateName)");
		BaseDTO response = stateMasterService.getstateName(stateName);
		//log.info("Finished Execution of StateMasterController.getstateName(String stateName)");
		return response;
	}
	
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
   // @PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@PostMapping("/lazylist")
	public BaseDTO lazylist(@Valid @RequestBody PaginationRequestDTO dto) {
	//log.info("Entered in StateMasterController.saveState(StateMasterDTO dto)");
	BaseDTO response = stateMasterService.getAllState(dto);
	//log.info("Finished Execution of StateMasterController.saveState(StateMasterDTO dto)");
	return response;
	}

}
