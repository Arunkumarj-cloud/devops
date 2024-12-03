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
import com.oasys.uppcl_user__master_management.dto.BaseDTOMinMax;
import com.oasys.uppcl_user__master_management.dto.ServiceCategoryDTO;
import com.oasys.uppcl_user__master_management.service.ServiceCategoryService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/servicecategory")
@Log4j2
public class ServiceCategoryController {
	@Autowired(required=true)
	ServiceCategoryService  serviceCategoryService;
	
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@PostMapping("/create")
	public BaseDTO create(@Valid @RequestBody ServiceCategoryDTO dto) {
		//log.info("<--- ServiceCategoryController create  STARTED --->");
		BaseDTO response = serviceCategoryService.create(dto);
		//log.info("<--- ServiceCategoryController create  END --->");
		return response;
	}

	@DeleteMapping("/delete/{id}")
	public BaseDTO delete(@PathVariable("id") UUID id) {
		//log.info("<--- ServiceCategoryController delete  STARTED --->");
		BaseDTO response = serviceCategoryService.delete(id);
		//log.info("<--- ServiceCategoryController delete  END --->");
		return response;
	}

	@GetMapping("/getallactive")
	public BaseDTO getAllActive() {
		//log.info("<--- ServiceCategoryController getAllActive  STARTED --->");
		BaseDTO response = serviceCategoryService.getAllActive();
		//log.info("<--- ServiceCategoryController getAllActive  END --->");
		return response;
	}
    
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@PutMapping("/update/{id}")
	public BaseDTO update(@PathVariable("id") UUID id,@Valid @RequestBody ServiceCategoryDTO dto) {
		//log.info("<--- ServiceCategoryController update  STARTED --->");
		BaseDTO response = serviceCategoryService.update(id,dto);
		//log.info("<--- ServiceCategoryController update  END --->");
		return response;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/get/{id}")
	public BaseDTO getById(@PathVariable("id") UUID id) {
		//log.info("<--- ServiceCategoryController getById  STARTED --->");
		BaseDTO response = serviceCategoryService.getById(id);
		//log.info("<--- ServiceCategoryController getById  END --->");
		return response;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@GetMapping("/getbyname/{name}")
	public BaseDTO getByName(@PathVariable("name") String name) {
		// log.info("<--- ServiceCategoryController getById STARTED --->");
		BaseDTO response = serviceCategoryService.getByName(name);
		// log.info("<--- ServiceCategoryController getById END --->");
		return response;
	}
	
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
		@GetMapping("/getChargesByName/{userUUID}/{subCategory}/{name}/{amount}")
		public BaseDTO getChargesByName(@PathVariable("userUUID") String userUUID,@PathVariable("subCategory") String subCategory, @PathVariable("name") String name, @PathVariable("amount") Double amount) {
			// log.info("<--- ServiceCategoryController getById STARTED --->");
			BaseDTO response = serviceCategoryService.getChargesByName(userUUID,name,amount,subCategory);
			// log.info("<--- ServiceCategoryController getById END --->");
			return response;
		}
		
		@GetMapping("/getAadhaarPayCharges/{userUUID}/{subCategory}/{name}/{amount}")
		public BaseDTO getAdhaarPayCharges(@PathVariable("userUUID") String userUUID,@PathVariable("subCategory") String subCategory, @PathVariable("name") String name, @PathVariable("amount") Double amount) {
			// log.info("<--- ServiceCategoryController getById STARTED --->");
			BaseDTO response = serviceCategoryService.getAdhaarPayCharges(userUUID,name,amount,subCategory);
			// log.info("<--- ServiceCategoryController getById END --->");
			return response;
		}
		
		//@PreAuthorize("isAuthenticated()")
		@GetMapping("/getFundTransferCharges/{userUUID}/{subCategory}/{name}/{amount}")
		public BaseDTO getFundTransferCharges(@PathVariable("userUUID") String userUUID,@PathVariable("subCategory") String subCategory, @PathVariable("name") String name, @PathVariable("amount") Double amount) {
			// log.info("<--- ServiceCategoryController getById STARTED --->");
			BaseDTO response = serviceCategoryService.getFundTransferCharges(userUUID,name,amount,subCategory);
			// log.info("<--- ServiceCategoryController getById END --->");
			return response;
		}
		
	//	@PreAuthorize("isAuthenticated()")
		@GetMapping("/getMinMaxAmount/{name}")
		public BaseDTOMinMax getMinMaxAmountByName(@PathVariable("name") String name) {
			// log.info("<--- ServiceCategoryController getById STARTED --->");
			BaseDTOMinMax response = serviceCategoryService.getMinMaxAmountByName(name);
			// log.info("<--- ServiceCategoryController getById END --->");
			return response;
		}
		
		@GetMapping("/all")
		public BaseDTO getAllOrderByModifiedDate() {
			
			BaseDTO response = serviceCategoryService.getAllOrderByModifiedDate();
			
			return response;
		}

}
