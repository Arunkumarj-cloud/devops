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
import com.oasys.uppcl_user__master_management.dto.ServiceSlotDTO;
import com.oasys.uppcl_user__master_management.service.ServiceSlotService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/serviceSlot")
@Log4j2
public class ServiceSlotController {

	@Autowired
	ServiceSlotService serviceSlotService;

	//@PreAuthorize(PrivilegeConstant.ADD_SLAB)
	@PostMapping("/create")
	public BaseDTO create(@Valid @RequestBody ServiceSlotDTO dto) {
		// log.info("<--- Started ServiceSlotController.create --->");
		BaseDTO baseDTO = serviceSlotService.create(dto);
		// log.info("<--- Ended ServiceSlotController.create --->");
		return baseDTO;
	}

   // @PreAuthorize("#oauth2.hasScope('Admin')")
    //@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/getallactive")
	public BaseDTO getAllActive() {
		// log.info("<--- ServiceSlotController getAll Controller STARTED --->");
		BaseDTO response = serviceSlotService.getAllActive();
		// log.info("<--- ServiceSlotController getAll Controller END --->");
		return response;
	}

	//@PreAuthorize(PrivilegeConstant.UPDATE_SLAB)
	@PutMapping("/update/{id}")
	public BaseDTO update(@PathVariable("id") UUID id, @Valid @RequestBody ServiceSlotDTO dto) {
		// log.info("<--- Started ServiceSlotController.update --->");
		BaseDTO baseDTO = serviceSlotService.update(id, dto);
		// log.info("<--- Ended ServiceSlotController.update --->");
		return baseDTO;
	}

	@GetMapping("/getByServiceCategoryId/{id}")
	public BaseDTO getByServiceCategoryId(@PathVariable("id") UUID id) {
		// log.info("<--- Started ServiceProviderController.getByServiceCategoryId
		// --->");
		BaseDTO baseDTO = serviceSlotService.getByServiceCategoryId(id);
		// log.info("<--- Ended ServiceProviderController.getByServiceCategoryId --->");
		return baseDTO;
	}

	//@PreAuthorize("#oauth2.hasScope('Admin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/get/{id}")
	public BaseDTO getById(@PathVariable("id") UUID id) {
		// log.info("<--- ServiceSlotController getById STARTED --->");
		BaseDTO response = serviceSlotService.getById(id);
		// log.info("<--- ServiceSlotController getById END --->");
		return response;
	}

	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@DeleteMapping("/delete/{id}")
	public BaseDTO delete(@PathVariable("id") UUID id) {
		// log.info("<--- Started ServiceSlotController.delete --->");
		BaseDTO baseDTO = serviceSlotService.delete(id);
		// log.info("<--- Ended ServiceSlotController.delete --->");
		return baseDTO;
	}

	
	@GetMapping("/get/{serviceCategoryId}/{amount}")
	//@PreAuthorize("isAuthenticated()")
	public BaseDTO getById(@PathVariable("serviceCategoryId") UUID id, @PathVariable("amount") Double amount) {
		BaseDTO response = serviceSlotService.get(id, amount);
		return response;
	}

	@GetMapping("/getSlots/{amount}")
   // @PreAuthorize("isAuthenticated()")
	public BaseDTO getById(@PathVariable("amount") Double amount) {
		BaseDTO response = serviceSlotService.getSlotids(amount);
		return response;
	}

}
