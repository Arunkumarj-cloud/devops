package com.oasys.uppcl_user__master_management.controller;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oasys.config.BaseDTO;
import com.oasys.uppcl_user__master_management.dto.CustomerRequest;
import com.oasys.uppcl_user__master_management.service.CustomerService;
import com.oasys.uppcl_user__master_management.validation.CustomerRequestValidate;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/customer")
@Log4j2
public class CustomerController {

	@Autowired
	CustomerRequestValidate validate;
	
	@Autowired
	CustomerService customerService;
	
	@PostMapping("/create")
	public BaseDTO createCustomerDetails(@Valid @RequestBody CustomerRequest customerRequest)  {
		BaseDTO response = new BaseDTO();
		response = validate.validate(customerRequest);
		if(response.getStatusCode()==200) {
		response = customerService.create(customerRequest);
		}
		return response;
	}
	@GetMapping("/get/{id}")
	public BaseDTO getCustomerDetails(@PathVariable("id") UUID id) {
		
		BaseDTO response = customerService.getDetails(id);
		return response;
	}
	@GetMapping("/getbySmartCardId/{id}")
	public BaseDTO getCustomerDetails(@PathVariable("id") String id) {
		
		BaseDTO response = customerService.getDetailsBySmartcard(id);
		return response;
	}
	

}
