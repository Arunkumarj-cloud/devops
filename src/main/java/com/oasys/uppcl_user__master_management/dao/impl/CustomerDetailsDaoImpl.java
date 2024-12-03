package com.oasys.uppcl_user__master_management.dao.impl;

import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;

import com.oasys.config.BaseDTO;
import com.oasys.config.ErrorDescription;
import com.oasys.uppcl_user__master_management.dao.CustomerDetailsDao;
import com.oasys.uppcl_user__master_management.dto.CustomerRequest;
import com.oasys.uppcl_user__master_management.entity.CustomerDetails;
import com.oasys.uppcl_user__master_management.repository.CustomerDetailsRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;

import lombok.extern.log4j.Log4j2;

@Repository
@Log4j2
public class CustomerDetailsDaoImpl implements CustomerDetailsDao {

	@Autowired
	CustomerDetailsRepository customerRepository;
	
	@Autowired
	MessageSource messageSource;
	
	
	@Override
	public BaseDTO createCustomer(CustomerRequest request) {
		
		BaseDTO response = new BaseDTO();
		CustomerDetails entity = null;
		try {
			String message = "";
			Optional<CustomerDetails> optional=customerRepository.findBySmartCard(request.getSmartCardNumber());
			if(optional.isPresent()) {
				 entity = optional.get();
				entity.setBankName(request.getBankName());
				entity.setBranchName(request.getBranchName());
				entity.setCustomerName(request.getCustomerName());
				entity.setIfscCode(request.getIfscCode());
				entity.setPhoneNumber(request.getPhoneNumber());
				entity.setRationCardNumber(request.getRationCardNumber());
				entity.setStateName(request.getStateName());
				entity.setDistrictName(request.getDistrictName());
				entity.setStatus(1);
				entity.setAccountNumber(request.getAccountNumber());
				customerRepository.save(entity);
				
			}else {
				 entity = new CustomerDetails();
				entity.setBankName(request.getBankName());
				entity.setBranchName(request.getBranchName());
				entity.setCustomerName(request.getCustomerName());
				entity.setIfscCode(request.getIfscCode());
				entity.setPhoneNumber(request.getPhoneNumber());
				entity.setRationCardNumber(request.getRationCardNumber());
				entity.setSmartCardNumber(request.getSmartCardNumber());
				entity.setStateName(request.getStateName());
				entity.setDistrictName(request.getDistrictName());
				entity.setStatus(1);
				entity.setAccountNumber(request.getAccountNumber());
				customerRepository.save(entity);
			   }
			 
			
			message = messageSource.getMessage(ResponseConstant.CREATE_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
			response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
			response.setMessage(message);
			response.setResponseContent(entity);
		}catch(Exception e) {
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			log.error("Exception "+e);
		}
		
		return response;
	}

	@Override
	public BaseDTO getCustomer(UUID uuid) {
		BaseDTO response=new BaseDTO();
		Optional<CustomerDetails> optional=customerRepository.findById(uuid);
		if(optional.isPresent()) {
			response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
			response.setMessage("Customer Details Fetched Successfully.");
			response.setResponseContent(optional);
		}else {
			response.setMessage("No Record Found..");
			response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
		}
		return response;
	}

	@Override
	public BaseDTO getCustomerBySmartCardId(String smartCardId) {
		BaseDTO response=new BaseDTO();
		Optional<CustomerDetails> optional=customerRepository.findBySmartCard(smartCardId);
		if(optional.isPresent()) {
			response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
			response.setMessage("Customer Details Fetched Successfully.");
			response.setResponseContent(optional);
		}else {
			response.setMessage("No Record Found..");
			response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
		}
		return response;
	}

}
