package com.oasys.uppcl_user__master_management.controller;

import java.util.List;
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
import com.oasys.uppcl_user__master_management.dto.ListDto;
import com.oasys.uppcl_user__master_management.dto.SearchDTO;
import com.oasys.uppcl_user__master_management.dto.SearchFiledsDto;
import com.oasys.uppcl_user__master_management.dto.ServiceDTO;
import com.oasys.uppcl_user__master_management.dto.ServiceDeatilsDto;
import com.oasys.uppcl_user__master_management.service.ServiceService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/service")
@Log4j2
public class ServiceController {
	
	@Autowired
	ServiceService ServiceService;
	
	

	//@PreAuthorize(PrivilegeConstant.ADD_SERVICE)
	@PostMapping("/create")
	public BaseDTO createService(@Valid @RequestBody ServiceDTO serviceDTO) {
		//log.info("<--- Service create Controller STARTED --->");
		BaseDTO response = ServiceService.create(serviceDTO);
		//log.info("<--- Service create Controller END --->");
		return response;
	}

	//@PreAuthorize("#oauth2.hasScope('Admin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/getall")
	public BaseDTO getAll() {
		//log.info("<--- Service getAll Controller STARTED --->");
		BaseDTO response = ServiceService.getAll();
		//log.info("<--- Service getAll Controller END --->");
		return response;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin') or #oauth2.hasScope('Customer Care Executive')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/getallactive")
	public BaseDTO getAllActive() {
		//log.info("<--- Service getAll Controller STARTED --->");
		BaseDTO response = ServiceService.getAllActive();
		//log.info("<--- Service getAll Controller END --->");
		return response;
	}

	//@PreAuthorize(PrivilegeConstant.UPDATE_SERVICE)
	@PutMapping("/update/{id}")
	public BaseDTO updateService(@PathVariable("id") UUID id,@Valid @RequestBody ServiceDTO serviceDTO) {
		//log.info("<--- Service update Controller STARTED --->");
		BaseDTO response = ServiceService.update(id,serviceDTO);
		//log.info("<--- Service update Controller END --->");
		return response;
	}
		
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@PostMapping("/lazylist")
	public BaseDTO getLazyList(@RequestBody PaginationRequestDTO requestData) {
	BaseDTO response = new BaseDTO();
	//log.info("<--- Service lazylist Controller STARTED --->");
	response = ServiceService.getLazyList(requestData);
	//log.info("<--- Service lazylist Controller END --->");
	return response;
	}

	//@PreAuthorize("#oauth2.hasScope('Admin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/get/{id}")
	public BaseDTO getById(@PathVariable("id") UUID id) {
		//log.info("<--- Service getById Controller STARTED --->");
		BaseDTO response = ServiceService.getById(id);
		//log.info("<--- Service getById Controller END --->");
		return response;
	}

	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@DeleteMapping("/delete/{id}")
	public BaseDTO deleteService(@PathVariable("id") UUID id) {
		//log.info("<--- Service delete Controller STARTED --->");
		BaseDTO response = ServiceService.delete(id);
		//log.info("<--- Service delete Controller END --->");
		return response;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@PutMapping("/softdelete/{id}")
	public BaseDTO softDelete(@PathVariable("id") UUID id) {
		//log.info("<--- Service softDelete Controller STARTED --->");
		BaseDTO response = ServiceService.softDelete(id);
		//log.info("<--- Service softDelete Controller END --->");
		return response;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin') or #oauth2.hasScope('Merchant')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@PostMapping("/getlist")
	public BaseDTO getList(@RequestBody ListDto list) {
		//log.info("<--- Service create Controller STARTED --->");
		BaseDTO response = ServiceService.getList(list);
		//log.info("<--- Service create Controller END --->");
		return response;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin') or #oauth2.hasScope('Customer Care Executive')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@PostMapping("/getallactivesearch")
	public BaseDTO getAllActiveWithSearch(@RequestBody SearchDTO searchDTO) {
		//log.info("<--- Service getAll Controller STARTED --->");
		BaseDTO response = ServiceService.getAllActiveWithSearch(searchDTO);
		//log.info("<--- Service getAll Controller END --->");
		return response;
	}
	
	//@PreAuthorize("isAuthenticated()")
	@PostMapping("/getServiceAndProviderList")
	public BaseDTO getServiceAndProviderList(@Valid @RequestBody List<ServiceDeatilsDto> serviceDeatilsDtos) {
		BaseDTO response = ServiceService.getServiceAndProviderList(serviceDeatilsDtos);
		return response;
	}
	
	@PostMapping("/post")
	public BaseDTO createSearchFields(@RequestBody SearchFiledsDto searchFiledsDto) {
		//log.info("<--- Service create Controller STARTED --->");
		BaseDTO response = ServiceService.createSearchFields(searchFiledsDto);
		//log.info("<--- Service create Controller END --->");
		return response;
	}
	
	@GetMapping("/getsearchfields/{id}")
	public BaseDTO getSearchFields(@PathVariable("id") UUID id) {
		//log.info("<--- Service getById Controller STARTED --->");
		BaseDTO response = ServiceService.getSearchFields(id);
		//log.info("<--- Service getById Controller END --->");
		return response;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/getservielist")
	public BaseDTO getServiceList() {
		//log.info("<--- Service getAll Controller STARTED --->");
		BaseDTO response = ServiceService.getServiceList();
		//log.info("<--- Service getAll Controller END --->");
		return response;
	}
	
}
