package com.oasys.uppcl_user__master_management.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oasys.config.BaseDTO;
import com.oasys.config.ErrorDescription;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping(value = "/merchant")
@Log4j2

public class referenceIdController {
	
	
	/*
	 * @PreAuthorize("#oauth2.hasScope('" + UserRoleConstants.ADMIN_ROLE + "') " +
	 * "or #oauth2.hasScope('" + UserRoleConstants.SO_ROLE + "')" +
	 * "or #oauth2.hasScope('" + UserRoleConstants.TENANT_ADMIN_ROLE + "')" +
	 * " or #oauth2.hasScope('" + UserRoleConstants.STATE_DISTRIBUTER + "')" +
	 * " or #oauth2.hasScope('" + UserRoleConstants.DISTRICT_DISTRIBUTER + "')" +
	 * " or #oauth2.hasScope('" + UserRoleConstants.SUPER_DISTRICT_DISTRIBUTER +
	 * "')" + "or #oauth2.hasScope('" + UserRoleConstants.SUPER_PINCODE_DISTRIBUTER
	 * + "')" + "or #oauth2.hasScope('" + UserRoleConstants.PINCODE_DISTRIBUTER +
	 * "')" + " ")
	 */
	
	//@PreAuthorize(PermissionsConstant.IS_AUTHENTICATE)
	@PostMapping("/referenceId")
	public BaseDTO generatingReferenceId() {
		Map<String, String> map = new HashMap<>();
		BaseDTO response = new BaseDTO();

		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMddHHmmssSSS");// dd/MM/yyyy
		Date now = new Date();
		String strDate = sdfDate.format(now);
		String referenceId = "MREF" + strDate;
		map.put("referenceNumber", referenceId);
		if (referenceId != null) {
			response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
			response.setResponseContent(map);
		}

		return response;

	}

}
