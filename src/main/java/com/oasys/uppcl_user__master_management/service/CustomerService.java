package com.oasys.uppcl_user__master_management.service;

import java.util.UUID;

import com.oasys.config.BaseDTO;
import com.oasys.uppcl_user__master_management.dto.CustomerRequest;

public interface CustomerService {

	BaseDTO create(CustomerRequest customerRequest);

	BaseDTO getDetails(UUID id);

	BaseDTO getDetailsBySmartcard(String id);

}
