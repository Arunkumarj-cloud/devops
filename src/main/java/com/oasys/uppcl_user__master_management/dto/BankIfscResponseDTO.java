package com.oasys.uppcl_user__master_management.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class BankIfscResponseDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String ifscCode;
	String bankName;
	String stateName;
	String branchName;
	String address;
	String branchContactNumber;
	String districtName;
	

}
