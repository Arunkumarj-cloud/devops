package com.oasys.uppcl_user__master_management.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oasys.config.BaseDTO;
import com.oasys.config.ErrorDescription;
import com.oasys.uppcl_user__master_management.dao.CommissionHierarchyDao;
import com.oasys.uppcl_user__master_management.service.CommissionHierarchyService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class CommissionHierarchyServiceImpl implements CommissionHierarchyService {

	@Autowired
	CommissionHierarchyDao commissionHierarchyDao;

	@Override
	public BaseDTO getAllActive(Boolean status) {
		BaseDTO response = new BaseDTO();
		try {
			response = commissionHierarchyDao.getAllActive(status);
		} catch (Exception e) {
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage(" Unable To Get Details");

		}

		return response;
	}
}
