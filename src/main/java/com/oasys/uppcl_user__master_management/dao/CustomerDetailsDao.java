package com.oasys.uppcl_user__master_management.dao;

import java.util.UUID;

import com.oasys.config.BaseDTO;
import com.oasys.uppcl_user__master_management.dto.CustomerRequest;

public interface CustomerDetailsDao {

	BaseDTO createCustomer(CustomerRequest request);

	BaseDTO getCustomer(UUID id);

	BaseDTO getCustomerBySmartCardId(String smartCardId);
}
