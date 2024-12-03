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
import com.oasys.uppcl_user__master_management.dto.ApiDetailsDTO;
import com.oasys.uppcl_user__master_management.service.ApiDetailsService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class apiDetailsController {
	
	@Autowired
	private ApiDetailsService api_details_service;

//	@PreAuthorize("#oauth2.hasScope('Admin')")
	@PostMapping("/create")
	public BaseDTO save(@Valid @RequestBody ApiDetailsDTO dto) {
		BaseDTO response = api_details_service.save(dto);
		//logger.info(" Execution of APIDetailsController.save(APIDetailsDTO dto) method is finished.");
		return response;
	}
	
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@PostMapping("/lazylist")
	public BaseDTO retrieve_all(@RequestBody PaginationRequestDTO dto) {
		//logger.info("APIDetailsController.retrieve_all() method is called.");
		BaseDTO response = api_details_service.retrieve_all(dto);
		//logger.info(" Execution of APIDetailsController.retrieve_all() method is finished.");
		return response;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
		//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
		@GetMapping("/get/{id}")
		public BaseDTO retrieve(@PathVariable("id") UUID id) {
			//logger.info("APIDetailsController.retrieve(APIDetailsDTO dto) method is called.");
			BaseDTO response = api_details_service.retrieve(id);
			//logger.info(" Execution of APIDetailsController.retrieve(APIDetailsDTO dto) method is finished.");
			return response;
		}
		//@PreAuthorize("#oauth2.hasScope('Admin')")
		@PutMapping("/update/{id}")
		public BaseDTO update(@PathVariable("id") UUID id, @Valid @RequestBody ApiDetailsDTO dto) {
			//logger.info("APIDetailsController.update(APIDetailsDTO dto) method is called.");
			BaseDTO response = api_details_service.update(id, dto);
			//logger.info(" Execution of APIDetailsController.update(APIDetailsDTO dto) method is finished.");
			return response;
		}
		//@PreAuthorize("#oauth2.hasScope('Admin')")
		@DeleteMapping("/softdelete/{id}")
		public BaseDTO softDelete(@PathVariable("id") UUID id) {
			//logger.info("<--- APIDetailsController softdelete Controller STARTED --->");
			BaseDTO response = api_details_service.softDelete(id);
			//logger.info("<--- APIDetailsController softdelete Controller END --->");
			return response;
		}

		//@PreAuthorize("#oauth2.hasScope('Admin')")
		@DeleteMapping("/delete/{id}")
		public BaseDTO deleteApi(@PathVariable("id") UUID id) {
			//logger.info("<--- APIDetailsController delete Controller STARTED --->");
			BaseDTO response = api_details_service.delete(id);
			//logger.info("<--- APIDetailsController delete Controller END --->");
			return response;
		}

		//@PreAuthorize("#oauth2.hasScope('Admin')")
		//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
		@GetMapping("/getallactive")
		public BaseDTO getAllActive() {
			//logger.info("<--- APIDetailsController getAll Controller STARTED --->");
			BaseDTO response = api_details_service.getAllActive();
			//logger.info("<--- APIDetailsController getAll Controller END --->");
			return response;
		}

	
		//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	  @GetMapping("/getall") 
	  public BaseDTO getAll() {
	  //logger.info("<--- APIDetailsController getAll Controller STARTED --->");
	  BaseDTO response = api_details_service.getAll();
	  //logger.info("<--- APIDetailsController getAll Controller END --->");
	  return response; }
	 

}




