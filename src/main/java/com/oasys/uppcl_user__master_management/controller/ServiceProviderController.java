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
import com.oasys.uppcl_user__master_management.dto.SearchDTO;
import com.oasys.uppcl_user__master_management.dto.ServiceProviderDTO;
import com.oasys.uppcl_user__master_management.service.ServiceProviderService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/serviceprovider")
@Log4j2
public class ServiceProviderController {


	@Autowired
	ServiceProviderService serviceProviderService;
	
	
	//@PreAuthorize(PrivilegeConstant.ADD_SERVICE_PROVIDER)
    @PostMapping("/create")
	public BaseDTO create(@Valid @RequestBody ServiceProviderDTO dto) {
		//log.info("<--- Started ServiceProviderController.create --->");
		BaseDTO baseDTO = serviceProviderService.create(dto);
		//log.info("<--- Ended ServiceProviderController.create --->");
		return baseDTO;
	}
	

	//@PreAuthorize("#oauth2.hasScope('Admin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/getallactive")
	public BaseDTO getAllActive() {
		//log.info("<--- ServiceProviderController getAll Controller STARTED --->");
		BaseDTO response = serviceProviderService.getAllActive();
		//log.info("<--- ServiceProviderController getAll Controller END --->");
		return response;
	}

	//@PreAuthorize(PrivilegeConstant.UPDATE_SERVICE_PROVIDER)
	@PutMapping("/update/{id}")
	public BaseDTO update(@PathVariable("id") UUID id, @Valid @RequestBody ServiceProviderDTO dto) {
		//log.info("<--- Started ServiceProviderController.update --->");
		BaseDTO baseDTO = serviceProviderService.update(id,dto);
		//log.info("<--- Ended ServiceProviderController.update --->");
		return baseDTO;
	}
    		
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@DeleteMapping("/delete/{id}")
	public BaseDTO delete(@PathVariable("id") UUID id) {
		//log.info("<--- Started ServiceProviderController.delete --->");
		BaseDTO baseDTO = serviceProviderService.delete(id);
		//log.info("<--- Ended ServiceProviderController.delete --->");
		return baseDTO;
	}
	
	@GetMapping("/getByServiceCategoryId/{id}")
	public BaseDTO getByServiceCategoryId(@PathVariable("id") UUID id) {
		//log.info("<--- Started ServiceProviderController.getByServiceCategoryId --->");
		BaseDTO baseDTO = serviceProviderService.getByServiceCategoryId(id);
		//log.info("<--- Ended ServiceProviderController.getByServiceCategoryId --->");
		return baseDTO;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/get/{id}")
	public BaseDTO getById(@PathVariable("id") UUID id) {
		//log.info("<--- ServiceProviderController getById  STARTED --->");
		BaseDTO response = serviceProviderService.getById(id);
		//log.info("<--- ServiceProviderController getById  END --->");
		return response;
	}
	

	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/getbyname/{name}")
	public BaseDTO getByName(@PathVariable("name") String name) {
		//log.info("<--- ServiceProviderController getById  STARTED --->");
		BaseDTO response = serviceProviderService.getByName(name);
		//log.info("<--- ServiceProviderController getById  END --->");
		return response;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@PostMapping("/getallactivewithsearch")
	public BaseDTO getAllActiveWithSearch(@RequestBody SearchDTO searchDTO) {
		//log.info("<--- ServiceProviderController getAll Controller STARTED --->");
		BaseDTO response = serviceProviderService.getAllActiveWithSearch(searchDTO);
		//log.info("<--- ServiceProviderController getAll Controller END --->");
		return response;
	}
	
	@GetMapping("/getbynameandid/{name}/{id}")
	public BaseDTO getByName(@PathVariable("name") String name,@PathVariable("id") UUID id) {
		//log.info("<--- ServiceProviderController getById  STARTED --->");
		BaseDTO response = serviceProviderService.getByNameAndId(name,id);
		//log.info("<--- ServiceProviderController getById  END --->");
		return response;
	}
	
	@GetMapping("/all")
	public BaseDTO getAllOrderByModifiedDate() {	
		BaseDTO response =serviceProviderService.getAllOrderByModifiedDate();
		return response;
	}

}