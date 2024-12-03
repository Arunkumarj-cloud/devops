package com.oasys.uppcl_user__master_management.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oasys.config.BaseDTO;
import com.oasys.uppcl_user__master_management.dao.CustomerDetailsDao;
import com.oasys.uppcl_user__master_management.dto.CustomerRequest;
import com.oasys.uppcl_user__master_management.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerDetailsDao customerDetailsDao;
	
	@Override
	public BaseDTO create(CustomerRequest request) {
		BaseDTO response = customerDetailsDao.createCustomer(request);
		return response;
		
	}

	@Override
	public BaseDTO getDetails(UUID id) {
		BaseDTO response = customerDetailsDao.getCustomer(id);
		return response;
	}

	@Override
	public BaseDTO getDetailsBySmartcard(String smartCardNumber) {
		BaseDTO response = customerDetailsDao.getCustomerBySmartCardId(smartCardNumber);
		return response;
	}
}
