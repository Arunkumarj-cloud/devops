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
import com.oasys.uppcl_user__master_management.dto.SubscriptionMasterDTO;
import com.oasys.uppcl_user__master_management.service.SubscriptionMasterService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/subscription")
@Log4j2
public class SubscriptionMasterController {
	
	@Autowired
	SubscriptionMasterService subscriptionMasterService;

	@PostMapping("/create")
	public BaseDTO createSubscriptionMaster(@Valid @RequestBody SubscriptionMasterDTO subscriptionDTO) {
		//log.info("<--- SubscriptionMaster create Controller STARTED --->");
		BaseDTO response = subscriptionMasterService.create(subscriptionDTO);
		//log.info("<--- SubscriptionMaster create Controller END --->");
		return response;
	}
	
	
		@GetMapping("/getall")
		public BaseDTO getAll() {
			//log.info("<--- SubscriptionMaster getAll Controller STARTED --->");
			BaseDTO response = subscriptionMasterService.getAll();
			//log.info("<--- SubscriptionMaster getAll Controller END --->");
			return response;
		}
		
		
		@GetMapping("/getallactive")
		public BaseDTO getAllActive() {
			//log.info("<--- SubscriptionMaster getAll Controller STARTED --->");
			BaseDTO response = subscriptionMasterService.getAllActive();
			//log.info("<--- SubscriptionMaster getAll Controller END --->");
			return response;
		}

		
		@PutMapping("/update/{id}")
		public BaseDTO updateSubscriptionMaster(@PathVariable("id") UUID id,@Valid @RequestBody SubscriptionMasterDTO subscriptionDTO) {
			//log.info("<--- SubscriptionMaster update Controller STARTED --->");
			BaseDTO response = subscriptionMasterService.update(id,subscriptionDTO);
			//log.info("<--- SubscriptionMaster update Controller END --->");
			return response;
		}
			
		
		@PostMapping("/lazylist")
		public BaseDTO getLazyList(@RequestBody PaginationRequestDTO requestData) {
		//log.info("<--- SubscriptionMaster lazylist Controller STARTED --->");
		BaseDTO response =subscriptionMasterService.getLazyList(requestData);
		//log.info("<--- SubscriptionMaster lazylist Controller END --->");
		return response;
		}

		
		@GetMapping("/get/{id}")
		public BaseDTO getById(@PathVariable("id") UUID id) {
			//log.info("<--- SubscriptionMaster getById Controller STARTED --->");
			BaseDTO response = subscriptionMasterService.getById(id);
			//log.info("<--- SubscriptionMaster getById Controller END --->");
			return response;
		}

		
		@DeleteMapping("/delete/{id}")
		public BaseDTO deleteSubscriptionMaster(@PathVariable("id") UUID id) {
			//log.info("<--- SubscriptionMaster delete Controller STARTED --->");
			BaseDTO response = subscriptionMasterService.delete(id);
			//log.info("<--- SubscriptionMaster delete Controller END --->");
			return response;
		}
		
		
		@PutMapping("/softdelete/{id}")
		public BaseDTO softDelete(@PathVariable("id") UUID id) {
			//log.info("<--- SubscriptionMaster delete Controller STARTED --->");
			BaseDTO response = subscriptionMasterService.softDelete(id);
			//log.info("<--- SubscriptionMaster delete Controller END --->");
			return response;
		}
		
		//@PreAuthorize("#oauth2.hasScope('Admin')")
		
		@GetMapping("/getdefault")
		public BaseDTO getDefault() {
			//log.info("<--- SubscriptionMaster getById Controller STARTED --->");
			BaseDTO response = subscriptionMasterService.getDefault();
			//log.info("<--- SubscriptionMaster getById Controller END --->");
			return response;
		}
		
		//@PreAuthorize("#oauth2.hasScope('Admin')")
		
		@GetMapping("/findbysub/{name}")
		public BaseDTO findbysubUUID(@PathVariable("name") String name) {
			BaseDTO response = new BaseDTO();
			response = subscriptionMasterService.findbysubUUID(name);
			return response;
		}

}
