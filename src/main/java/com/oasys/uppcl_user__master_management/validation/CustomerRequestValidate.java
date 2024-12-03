package com.oasys.uppcl_user__master_management.validation;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.oasys.config.BaseDTO;
import com.oasys.config.ErrorDescription;
import com.oasys.uppcl_user__master_management.dto.CustomerRequest;

@Component
public class CustomerRequestValidate {

	public BaseDTO validate(CustomerRequest request)  {
		
		BaseDTO res = new BaseDTO();
		if (StringUtils.isEmpty(request.getRationCardNumber()) 
				|| StringUtils.isEmpty(request.getAccountNumber())
				|| StringUtils.isEmpty(request.getBankName())		
				|| StringUtils.isEmpty(request.getIfscCode())
				|| StringUtils.isEmpty(request.getCustomerName())
				|| StringUtils.isEmpty(request.getSmartCardNumber())
				|| StringUtils.isEmpty(request.getPhoneNumber())
			) {
			res.setStatusCode(204);
			res.setMessage("Please fill the required fields");
	       
			
		}else {
		res.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());;
		}
				
	  return res;	
		
	}

}
